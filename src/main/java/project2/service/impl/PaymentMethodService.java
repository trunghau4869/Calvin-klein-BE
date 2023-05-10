package project2.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.dto.PaymentDTO;
import project2.model.PaymentMethod;
import project2.model.Product;
import project2.repository.IPaymentMethodRepository;
import project2.service.IPaymentMethodService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService implements IPaymentMethodService {

    //SELLER INFOR
    private static final String CLIENT_ID = "AWjQfUJ2fMAbZDPbeyzARVCZW6q-09J5plD6JUOKVAUiksF51lpdpeT1KSpyeWrq5UU0txtxmXokRYfq";
    private static final String CLIENT_SECRET = "EEVxrdhjPN0fRkGLpZF8iKk84RzqEbNhuYV5-NTJHf3SBWBZczTYwtULBL6NwHbUHjlx3ceLh9Cl6NlS";
    private static final String MODE = "sandbox";

    //RESPONSE URL CANCEL & SUCCESS AFTER BUYER PAYMENT
    private static final String URL_CANCEL = "http://localhost:8080/manager/payment/api/cancelUrl";
    private static final String URL_SUCCSESS = "http://localhost:8080/manager/payment/api/successUrl";

    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getAllPaymentMethod() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id);
    }

    //I : SEND TO PAYPAL ABOUT PAYER, TRANSACTION; O : RESPONSE URL FROM CHECKOUT

    public String authorizePayment(PaymentDTO paymentDTO) throws PayPalRESTException {
        Payer payer = getPayerInformation(paymentDTO);
        List<Transaction> transactionList = getTransactionInformation(paymentDTO);
        RedirectUrls redirectUrls = getRedirectURLs();
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(transactionList)
                .setRedirectUrls(redirectUrls)
                .setPayer(payer)
                //.setIntent("authorize"); //authorize : ủy quyền tạm giữ 29 ngày or đến khi hoàn thành giao dịch
                .setIntent("sale"); //capture : thu tiền ngay lập tức từ người mua
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);
        System.out.println(approvedPayment);
        return getApprovalLink(approvedPayment);
    }
    //USE TO GET URL FORM PAYPAL
    private String getApprovalLink(Payment approvedPayment){
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;
        for(Links link : links){
            if(link.getRel().equals("approval_url")){
                approvalLink = link.getHref();
            }
        }
        return approvalLink;
    }

    public List<Transaction> getTransactionInformation(project2.dto.PaymentDTO paymentDTO){
        List<Product> products = new ArrayList<>();
        Double subTotal = 0.0;
        Double tax = 0.0 ;
        for(Product product : paymentDTO.getProduct()){
            products.add(product);
            subTotal += product.getFinalPrice();
            tax = tax + 1;
        }
        for(Product product : products){
            System.out.println("product name: " + product.getNameProduct());
        }
        System.out.println("sub " + subTotal);
        System.out.println("tax " + tax);
        Details details = new Details();
        details.setShipping(String.format("%.2f", Float.parseFloat("0")));
//        details.setFee(String.format("%.2f", Float.parseFloat("2")));
        details.setSubtotal(String.format("%.2f", subTotal));
        details.setTax(String.format("%.2f", tax));

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f",paymentDTO.getTotal()));
        System.out.println(paymentDTO.getTotal());
        amount.setDetails(details);


        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
//        transaction.setDescription(products.get(1).getNameProduct());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();



        for(int i = 0; i < products.size(); i++){
            Item item = new Item();
            item.setCurrency("USD")
                    .setName(products.get(i).getNameProduct())
                    .setPrice(String.format("%.2f", products.get(i).getFinalPrice()))
                    .setTax(String.format("%.2f", Float.parseFloat("1")))
                    .setQuantity("1");
            items.add(item);
        }
        itemList.setItems(items);
        transaction.setItemList(itemList);
        List<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList.add(transaction);
        return transactionList;
    }

    //USE TO SET URL REDIRECT
    public RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(URL_CANCEL);
        redirectUrls.setReturnUrl(URL_SUCCSESS);
        return redirectUrls;
    }
    //CHECK SUCCESS AND FAIL AFTER PAYMENTED
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return payment.execute(apiContext, paymentExecute);
    }

    public Payer getPayerInformation(project2.dto.PaymentDTO paymentDTO) {
        Payer payer = new Payer();
        payer.setPaymentMethod(paymentDTO.getPaymentMethod().getNamePaymentMethod());
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(paymentDTO.getFirstNameReceiver())
                .setLastName(paymentDTO.getLastNameReceiver())
                .setEmail(paymentDTO.getEmailReceiver());
        payer.setPayerInfo(payerInfo);
        return payer;
    }
}