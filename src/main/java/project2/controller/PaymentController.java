package project2.controller;

import com.google.gson.Gson;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project2.dto.PaymentDTO;
import project2.model.*;
import project2.service.*;
import project2.model.PaymentMethod;
import project2.model.Product;
import project2.model.Transport;
import project2.service.IProductService;
import project2.service.impl.PaymentMethodService;
import project2.service.impl.PaymentService;
import project2.service.impl.TransportService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manager/payment/api")
@CrossOrigin(origins = "*")
public class PaymentController {

    private static final Gson gson = new Gson();

    @Autowired
    private IInvoiceDetailService invoiceDetailService;

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private IBiddingStatusService biddingStatusService;

    @ModelAttribute("cart")
    public PaymentDTO setupCart(){
        return new PaymentDTO();
    }

//    Get list payment method
    @RequestMapping(value = "/payment-method", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethod();
        if (paymentMethods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paymentMethods, HttpStatus.OK);
    }

    @RequestMapping(value = "/transport", method = RequestMethod.GET)
    public ResponseEntity<List<Transport>> getTransports() {
        List<Transport> transports = transportService.getAllTransport();
        if (transports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transports, HttpStatus.OK);
    }

    @RequestMapping(value = "/authorize_payment", method = RequestMethod.POST)
    public ResponseEntity<String> authorizePayment(@RequestBody project2.dto.PaymentDTO paymentDTO) throws PayPalRESTException {
        System.out.println(paymentDTO.getMember().getNameMember());
        paymentMethodService.getPayerInformation(paymentDTO);
        String approvalLink = paymentMethodService.authorizePayment(paymentDTO);
        if (approvalLink == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(gson.toJson(approvalLink));
    }

    @RequestMapping(value = "/cancelUrl", method = RequestMethod.GET)
    public void method(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "http://localhost:4200/payment/payment-cart");
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/successUrl")
    public void successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletResponse httpServletResponse){
        try {
            com.paypal.api.payments.Payment payment = paymentMethodService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                httpServletResponse.setHeader("Location", "http://localhost:4200/payment/invoice-status");
            }
            else {
                httpServletResponse.setHeader("Location", "http://localhost:4200/payment/payment-cart");
            }
            httpServletResponse.setStatus(302);
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
    }

    //QuangNV write method get product in cart
    @GetMapping("/getProduct/{idMember}")
    public ResponseEntity<List<Product>> getProductCart(@PathVariable Integer idMember){
        List<Product> productList = iProductService.getProductInCart(idMember);
        if (productList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }

    //QuangNV write method save payment
    @PostMapping("/savePayment")
    public ResponseEntity<project2.model.Payment> createPayment(@Valid @RequestBody PaymentDTO paymentDTO, BindingResult bindingResult){
        List<BiddingStatus> biddingStatusList = biddingStatusService.findByAll();
        if (bindingResult.hasFieldErrors()){
            System.out.println("ss");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {

            project2.model.Payment payment = new project2.model.Payment();
            payment.setIdPayment(paymentDTO.getIdPayment());
            payment.setFullNameReceiver(paymentDTO.getFirstNameReceiver() + " " + paymentDTO.getLastNameReceiver());
            payment.setAddressReceiver(paymentDTO.getAddressReceiver() + ", " +paymentDTO.getWard() + ", " +paymentDTO.getDistrict() + ", " +paymentDTO.getCity());
            payment.setPhoneReceiver(paymentDTO.getPhoneReceiver());
            payment.setEmailReceiver(paymentDTO.getEmailReceiver());
            payment.setFeeService(paymentDTO.getFeeService());
            payment.setDescriptionReceiver(paymentDTO.getDescriptionReceiver());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            payment.setMember(paymentDTO.getMember());
            payment.setTransport(paymentDTO.getTransport());
            payment.setCart(paymentDTO.getCart());
            // Check total and set flag Delete Product
            List<Product> productList = paymentDTO.getProduct();
            Double total = 0.0;
            for (Integer i = 0 ; i< productList.size() ; i++){
                productList.get(i).setFlagDelete(true);
                productList.get(i).setBiddingStatus(biddingStatusList.get(2));
                productList.get(i).setCart(payment.getCart());
                total = total + productList.get(i).getFinalPrice();
            }
            total += new Double(productList.size()*1);
            // Check total fe success and save
            if (total.intValue() == paymentDTO.getTotal().intValue()){
                iProductService.saveListProduct(productList);
                project2.model.Payment payment1 = paymentService.save(payment);


                // set invoice and invoice detail
                Invoice invoice = new Invoice();
                invoice.setFlagDelete(false);
                invoice.setMember(paymentDTO.getMember());
                invoice.setPayment(paymentService.getPaymentEnd());
                LocalDate date = LocalDate.now();
                invoice.setDateCreated(date);
                invoice.setIdStatusInvoice(true);
                invoice.setTotalPrice(paymentDTO.getTotal());
                invoiceService.save(invoice);
                //Set invoice detail
                List<InvoiceDetail> invoiceDetailList = new ArrayList<>();
                for (int i = 0 ; i<paymentDTO.getProduct().size();i++){
                    InvoiceDetail invoiceDetail = new InvoiceDetail();
                    invoiceDetail.setInvoice(invoice);
                    invoiceDetail.setProduct(paymentDTO.getProduct().get(i));
                    invoiceDetailList.add(invoiceDetail);
                }
                invoiceDetailService.saveList(invoiceDetailList);
                return new ResponseEntity<>(payment1, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    //QuangNV write method get all payment
    @GetMapping("/getPayment")
    public ResponseEntity<List<project2.model.Payment>> getAllPayment1(){
        List<project2.model.Payment> paymentList = paymentService.getAllPayment();
        if (paymentList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(paymentList, HttpStatus.OK);
        }
    }

    //QuangNV write method get all member
    @GetMapping("/getMember/{id_member}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id_member){
        Member member = memberService.findByIdMember(Long.parseLong(id_member)).get();
        if (member ==  null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(member, HttpStatus.OK);
        }
    }

    //QuangNV write method get Cart by ID member
    @GetMapping("/getCart/{id_member}")
    public ResponseEntity<Cart> getCart(@PathVariable String id_member){
        Long id_member1 = Long.parseLong(id_member);
        Cart cart = cartService.findByIdMember(id_member1);
        if (cart == null){
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
    }
}
