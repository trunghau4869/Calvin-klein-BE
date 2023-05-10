package project2.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project2.config.SmtpAuthenticator;
import project2.model.Product;
import project2.service.IProductService;

import java.io.IOException;

import project2.model.*;
import project2.service.*;
import project2.service.impl.ImageProductService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import project2.model.Cart;
import project2.model.Member;
import project2.service.ICartService;
import project2.service.IMemberService;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import project2.service.impl.TypeProductService;


import org.springframework.mail.javamail.MimeMessageHelper;
import project2.dto.AuctionDTO;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;
import java.util.*;


@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/manager/product/api")

public class ProductController {
    @Autowired
    private TypeProductService typeProductService;

    @Autowired
    private ImageProductService imageProductService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IBiddingStatusService biddingStatusService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IApprovalStatusService approvalStatusService;

    @Autowired
    private SmtpAuthenticator smtpAuthenticator;

    @Autowired
    private ICartService iCartService;


    // SamTV
    @PostMapping
    public ResponseEntity saveProduct(Product product, Long idPoster, List<MultipartFile> multipartFiles) throws IOException {
        return this.productService.save(product,idPoster, multipartFiles);
    }


    @Autowired
    private IAccountService iAccountService;

    //  BachLT
    @GetMapping("/statistic/{statsBegin}&{statsEnd}&{biddingStatus}")
    public ResponseEntity<List<Product>> statsProductFromDateToDate(@PathVariable Optional<String> statsBegin, @PathVariable Optional<String> statsEnd, @PathVariable int biddingStatus) {
        System.out.println(statsBegin.get() + "?- ?" + statsEnd.get() + "/? " + biddingStatus);
        List<Product> productList = productService.getAllProductByEndDate(statsBegin.get(), statsEnd.get(), biddingStatus);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    //  BachLT
    @GetMapping("/statistic/currentMonth&biddingStatus")
    public ResponseEntity<List<Product>> statsProductCurrentMonth(@RequestParam("currentMonth") int curMonth, @RequestParam("biddingStatus") int biddingStatus) {
        System.out.println(curMonth + "?- ?" + biddingStatus);
        List<Product> productList = productService.getAllProductAtCurrentMonth(curMonth, biddingStatus);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    //HieuDV
    @GetMapping("/list")
        public ResponseEntity<Iterable<Product>> getAllNotDeletedYet(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> productList = productService.getAllNotDeletedYet(pageable);
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    //HieuDV
    @PatchMapping("/delete/{id}")
    public ResponseEntity<InvoiceDetail> delete(@PathVariable Long id) {
        productService.setFlagDeleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //HuyNN
    Map<Long, List<AuctionDTO>> auctionList = new HashMap<>();

    //HuyNN
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    //HuyNN
    @PostMapping("/createNewAuction/{productId}")
    public ResponseEntity createNewAuction(@RequestBody AuctionDTO auctionDTO, @PathVariable Long productId) {
        if (this.auctionList.containsKey(productId)) {
            List<AuctionDTO> auctionDTOList = this.auctionList.get(productId);
            auctionDTOList.add(0, auctionDTO);
            this.auctionList.put(productId, auctionDTOList);
            this.updateCurrentPrice(productId, auctionDTO.getPrice());
            return new ResponseEntity(auctionDTOList, HttpStatus.OK);
        } else {
            List<AuctionDTO> auctionDTOList = new ArrayList<>();
            auctionDTOList.add(auctionDTO);
            auctionList.put(productId, auctionDTOList);
            this.updateCurrentPrice(productId, auctionDTO.getPrice());
            return new ResponseEntity(this.auctionList, HttpStatus.OK);
        }
    }

    //HuyNN
    public void updateCurrentPrice(Long productId, Double price) {
        Product product = productService.getProductById(productId);
        product.setFinalPrice(price);
        productService.updateCurrentPrice(product);
    }

    //HuyNN
    @GetMapping("/getCartByMemberId/{id}")
    public ResponseEntity<Cart> getCartByMemberId(@PathVariable Long id) {
        Cart cart = this.iCartService.findByIdMember(id);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    //HuyNN
    @GetMapping("/getAuctionList/{productId}")
    public ResponseEntity getAuctionList(@PathVariable Long productId) {
        if (this.auctionList.containsKey(productId)) {
            List<AuctionDTO> auctionDTOList = this.auctionList.get(productId);
            return new ResponseEntity(auctionDTOList, HttpStatus.OK);
        } else {
            List<AuctionDTO> auctionDTOList = new ArrayList<>();
            auctionList.put(productId, auctionDTOList);
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }

    //HuyNN
    @GetMapping("/getImageByProductId/{id}")
    public ResponseEntity<List<ImageProduct>> getImageByProductId(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<List<ImageProduct>>(imageProductService.findByProduct(product), HttpStatus.OK);
    }

    //HuyNN
    @GetMapping("/addProductToCart/{idMember}/{idProduct}")
    public ResponseEntity addProductToCart(@PathVariable Long idMember, @PathVariable Long idProduct) {
        Cart cart = iCartService.findByIdMember(idMember);
        if (cart == null) {
            this.iCartService.createCart("0", idMember);
            Cart newCart = iCartService.findByIdMember(idMember);
            Product product = this.productService.getProductById(idProduct);
            product.setCart(newCart);
            productService.updateIdCard(product);
        } else {
            Product product = this.productService.getProductById(idProduct);
            product.setCart(cart);
            productService.updateIdCard(product);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    //HuyNN
    @PutMapping("/updateCart")
    public ResponseEntity updateCart(@RequestBody Cart cart) {
        this.iCartService.updateCart(cart);
        return new ResponseEntity(HttpStatus.OK);
    }

    //HuyNN
    @GetMapping("/getAccountByUsername/{username}")
    public ResponseEntity<Account> getAccountByUsername(@PathVariable String username) {
        Account account = this.iAccountService.getAccountByUsername(username);
        return new ResponseEntity(account, HttpStatus.OK);
    }

    @GetMapping("/getMemberByIdAccount/{idAccount}")
    public ResponseEntity<Member> getMemberByIdAccount(@PathVariable Long idAccount) {
        Member member = this.memberService.findByIdAccount(idAccount);
        return new ResponseEntity(member, HttpStatus.OK);
    }

    @GetMapping("/updateIdBindingStatus/{idProduct}/{idBindingStatus}")
    public ResponseEntity updateIdBindingStatus(@PathVariable Long idProduct, @PathVariable Long idBindingStatus) {
        Product product = this.productService.getProductById(idProduct);
        BiddingStatus biddingStatus = this.biddingStatusService.findById(idBindingStatus);
        product.setBiddingStatus(biddingStatus);
        this.productService.saveProduct(product);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/blockMemberAndAccount/{idMember}/{idAccount}")
    public ResponseEntity blockMemberAndAccount(@PathVariable Long idMember, @PathVariable Long idAccount) {
        Member member = this.memberService.findByIdMember(idMember).get();
        member.setFlagDelete(true);
        this.memberService.save(member);
        Account account = this.iAccountService.findById(idAccount).get();
        account.setFlagDelete(true);
        this.iAccountService.save(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    //HuyNN
    @GetMapping("/sendPaymentEmail/{email}/{productName}")
    public ResponseEntity sendEmailAuctionProduct(@PathVariable String email, @PathVariable String productName) {
        String paymentLink = "http://localhost:4200/payment/payment-cart";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.stmp.user", "a0721i1.2022@gmail.com");

        //To use TLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.password", "ykddrsefedbcbvos");
        //To use SSL
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, smtpAuthenticator);
        String to = email;
        String from = "contact@a0721i1.com";
        String subject = "Payment For Auction Product " + productName;
        MimeMessage msg = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

        try {
            helper.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            helper.setSubject(subject);
            String content = "<table align='center' border='0' cellpadding='0' cellspacing='0' height='100%' id='bodyTable' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                    " 100%; background-color: #fed149; height: 100%; margin: 0; padding: 0; width:\n" +
                    " 100%' width='100%'>\n" +
                    "      <tr>\n" +
                    "        <td align='center' id='bodyCell' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; border-top: 0;\n" +
                    " height: 100%; margin: 0; padding: 0; width: 100%' valign='top'>\n" +
                    "          <!-- BEGIN TEMPLATE // -->\n" +
                    "          <!--[if gte mso 9]>\n" +
                    "              <table align='center' border='0' cellspacing='0' cellpadding='0' width='600' style='width:600px;'>\n" +
                    "                <tr>\n" +
                    "                  <td align='center' valign='top' width='600' style='width:600px;'>\n" +
                    "                  <![endif]-->\n" +
                    "          <table border='0' cellpadding='0' cellspacing='0' class='templateContainer' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; max-width:\n" +
                    " 600px; border: 0' width='100%'>\n" +
                    "            <tr>\n" +
                    "              <td id='templatePreheader' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #fed149;\n" +
                    " border-top: 0; border-bottom: 0; padding-top: 16px; padding-bottom: 8px' valign='top'>\n" +
                    "                <table border='0' cellpadding='0' cellspacing='0' class='mcnTextBlock' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " min-width:100%;' width='100%'>\n" +
                    "                  <tbody class='mcnTextBlockOuter'>\n" +
                    "                    <tr>\n" +
                    "                      <td class='mcnTextBlockInner' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%' valign='top'>\n" +
                    "                        <table align='left' border='0' cellpadding='0' cellspacing='0' class='mcnTextContentContainer' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                    " 100%; min-width:100%;' width='100%'>\n" +
                    "                          <tbody>\n" +
                    "                            <tr>\n" +
                    "                              <td class='mcnTextContent' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; word-break: break-word;\n" +
                    " color: #2a2a2a; font-family: 'Asap', Helvetica, sans-serif; font-size: 12px;\n" +
                    " line-height: 150%; text-align: left; padding-top:9px; padding-right: 18px;\n" +
                    " padding-bottom: 9px; padding-left: 18px;' valign='top'>\n" +
                    "                                <a href='https://www.lingoapp.com' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color: #2a2a2a;\n" +
                    " font-weight: normal; text-decoration: none' target='_blank' title='Lingo is the\n" +
                    " best way to organize, share and use all your visual assets in one place -\n" +
                    " all on your desktop.'>\n" +
                    "                                  <img align='none' alt='Lingo is the best way to\n" +
                    " organize, share and use all your visual assets in one place - all on your desktop.' height='32' src='https://firebasestorage.googleapis.com/v0/b/sprint2-1452b.appspot.com/o/image.png?alt=media&token=683228a2-2155-425e-beab-987b8a401089' style='-ms-interpolation-mode: bicubic; border: 0; outline: none;\n" +
                    " text-decoration: none; height: auto; width: 107px; height: 32px; margin: 0px;' width='107' />\n" +
                    "                                </a>\n" +
                    "                              </td>\n" +
                    "                            </tr>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td id='templateHeader' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: white;\n" +
                    " border-top: 0; border-bottom: 0; padding-top: 16px; padding-bottom: 0' valign='top'>\n" +
                    "                <table border='0' cellpadding='0' cellspacing='0' class='mcnImageBlock' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " min-width:100%;' width='100%'>\n" +
                    "                  <tbody class='mcnImageBlockOuter'>\n" +
                    "                    <tr>\n" +
                    "                      <td class='mcnImageBlockInner' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding:0px' valign='top'>\n" +
                    "                        <table align='left' border='0' cellpadding='0' cellspacing='0' class='mcnImageContentContainer' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                    " 100%; min-width:100%;' width='100%'>\n" +
                    "                          <tbody>\n" +
                    "                            <tr>\n" +
                    "                              <td class='mcnImageContent' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-right: 0px;\n" +
                    " padding-left: 0px; padding-top: 0; padding-bottom: 0; text-align:center;' valign='top'>\n" +
                    "                                <a class='' href='https://www.lingoapp.com' style='mso-line-height-rule:\n" +
                    " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color:\n" +
                    " #f57153; font-weight: normal; text-decoration: none' target='_blank' title=''>\n" +
                    "                                  <a class='' href='https://www.lingoapp.com/' style='mso-line-height-rule:\n" +
                    " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color:\n" +
                    " #f57153; font-weight: normal; text-decoration: none' target='_blank' title=''>\n" +
                    "                                    <img align='center' alt='Forgot your password?' class='mcnImage' src='https://img.freepik.com/premium-vector/online-payment-technology-concept-cashless-society-safety-payment-bills-coins-credit-card-pay-online-with-smartphone-flat-design-illustration_73903-384.jpg?w=2000' style='-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                    " text-decoration: none; vertical-align: bottom; max-width:1200px; padding-bottom:\n" +
                    " 0; display: inline !important; vertical-align: bottom;' width='600'></img>\n" +
                    "                                  </a>\n" +
                    "                                </a>\n" +
                    "                              </td>\n" +
                    "                            </tr>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td id='templateBody' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: white;\n" +
                    " border-top: 0; border-bottom: 0; padding-top: 0; padding-bottom: 0' valign='top'>\n" +
                    "                <table border='0' cellpadding='0' cellspacing='0' class='mcnTextBlock' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;' width='100%'>\n" +
                    "                  <tbody class='mcnTextBlockOuter'>\n" +
                    "                    <tr>\n" +
                    "                      <td class='mcnTextBlockInner' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%' valign='top'>\n" +
                    "                        <table align='left' border='0' cellpadding='0' cellspacing='0' class='mcnTextContentContainer' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                    " 100%; min-width:100%;' width='100%'>\n" +
                    "                          <tbody>\n" +
                    "                            <tr>\n" +
                    "                              <td class='mcnTextContent' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; word-break: break-word;\n" +
                    " color: #2a2a2a; font-family: Asap, Helvetica, sans-serif; font-size: 16px;\n" +
                    " line-height: 150%; text-align: center; padding-top:9px; padding-right: 18px;\n" +
                    " padding-bottom: 9px; padding-left: 18px;' valign='top'>\n" +
                    "\n" +
                    "                                <h1 class='null' style='color: #2a2a2a; font-family: Asap, Helvetica,\n" +
                    " sans-serif; font-size: 32px; font-style: normal; font-weight: bold; line-height:\n" +
                    " 125%; letter-spacing: 2px; text-align: center; display: block; margin: 0;\n" +
                    " padding: 0'><span style='text-transform:uppercase'>" + productName + "</span></h1>\n" +
                    "\n" +
                    "\n" +
                    "                                <h2 class='null' style='color: #2a2a2a; font-family: Asap, Helvetica,\n" +
                    " sans-serif; font-size: 24px; font-style: normal; font-weight: bold; line-height:\n" +
                    " 125%; letter-spacing: 1px; text-align: center; display: block; margin: 0;\n" +
                    " padding: 0'><span style='text-transform:uppercase'>Payment For Auction Product</span></h2>\n" +
                    "\n" +
                    "                              </td>\n" +
                    "                            </tr>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "                <table border='0' cellpadding='0' cellspacing='0' class='mcnTextBlock' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace:\n" +
                    " 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " min-width:100%;' width='100%'>\n" +
                    "                  <tbody class='mcnTextBlockOuter'>\n" +
                    "                    <tr>\n" +
                    "                      <td class='mcnTextBlockInner' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%' valign='top'>\n" +
                    "                        <table align='left' border='0' cellpadding='0' cellspacing='0' class='mcnTextContentContainer' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                    " 100%; min-width:100%;' width='100%'>\n" +
                    "                          <tbody>\n" +
                    "                            <tr>\n" +
                    "                              <td class='mcnTextContent' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; word-break: break-word;\n" +
                    " color: #2a2a2a; font-family: Asap, Helvetica, sans-serif; font-size: 16px;\n" +
                    " line-height: 150%; text-align: center; padding-top:9px; padding-right: 18px;\n" +
                    " padding-bottom: 9px; padding-left: 18px;' valign='top'>Click the button below to pay for the product.\n" +
                    "                                <br></br>\n" +
                    "                              </td>\n" +
                    "                            </tr>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "                <table border='0' cellpadding='0' cellspacing='0' class='mcnButtonBlock' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " min-width:100%;' width='100%'>\n" +
                    "                  <tbody class='mcnButtonBlockOuter'>\n" +
                    "                    <tr>\n" +
                    "                      <td align='center' class='mcnButtonBlockInner' style='mso-line-height-rule:\n" +
                    " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " padding-top:18px; padding-right:18px; padding-bottom:18px; padding-left:18px;' valign='top'>\n" +
                    "                        <table border='0' cellpadding='0' cellspacing='0' class='mcnButtonBlock' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;' width='100%'>\n" +
                    "                          <tbody class='mcnButtonBlockOuter'>\n" +
                    "                            <tr>\n" +
                    "                              <td align='center' class='mcnButtonBlockInner' style='mso-line-height-rule:\n" +
                    " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " padding-top:0; padding-right:18px; padding-bottom:18px; padding-left:18px;' valign='top'>\n" +
                    "                                <table border='0' cellpadding='0' cellspacing='0' class='mcnButtonContentContainer' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " border-collapse: separate !important;border-radius: 48px;background-color:\n" +
                    " #F57153;'>\n" +
                    "                                  <tbody>\n" +
                    "                                    <tr>\n" +
                    "                                      <td align='center' class='mcnButtonContent' style='mso-line-height-rule:\n" +
                    " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                    " font-family: Asap, Helvetica, sans-serif; font-size: 16px; padding-top:24px;\n" +
                    " padding-right:48px; padding-bottom:24px; padding-left:48px;' valign='middle'>\n" +
                    "                                        <a class='mcnButton ' href='" + paymentLink + "' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; display: block; color: #f57153;\n" +
                    " font-weight: normal; text-decoration: none; font-weight: normal;letter-spacing:\n" +
                    " 1px;line-height: 100%;text-align: center;text-decoration: none;color:\n" +
                    " #FFFFFF; text-transform:uppercase;' target='_blank' title='Review Lingo kit\n" +
                    " invitation'>Payment</a>\n" +
                    "                                      </td>\n" +
                    "                                    </tr>\n" +
                    "                                  </tbody>\n" +
                    "                                </table>\n" +
                    "                              </td>\n" +
                    "                            </tr>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "                <table border='0' cellpadding='0' cellspacing='0' class='mcnImageBlock' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;' width='100%'>\n" +
                    "                  <tbody class='mcnImageBlockOuter'>\n" +
                    "                    <tr>\n" +
                    "                      <td class='mcnImageBlockInner' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding:0px' valign='top'>\n" +
                    "                        <table align='left' border='0' cellpadding='0' cellspacing='0' class='mcnImageContentContainer' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                    " 100%; min-width:100%;' width='100%'>\n" +
                    "                          <tbody>\n" +
                    "                            <tr>\n" +
                    "                              <td class='mcnImageContent' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-right: 0px;\n" +
                    " padding-left: 0px; padding-top: 0; padding-bottom: 0; text-align:center;' valign='top'></td>\n" +
                    "                            </tr>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td id='templateFooter' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #fed149;\n" +
                    " border-top: 0; border-bottom: 0; padding-top: 8px; padding-bottom: 80px' valign='top'>\n" +
                    "                <table border='0' cellpadding='0' cellspacing='0' class='mcnTextBlock' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;' width='100%'>\n" +
                    "                  <tbody class='mcnTextBlockOuter'>\n" +
                    "                    <tr>\n" +
                    "                      <td class='mcnTextBlockInner' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%' valign='top'>\n" +
                    "                        <table align='center' bgcolor='white' border='0' cellpadding='32' cellspacing='0' class='card' style='border-collapse: collapse; mso-table-lspace: 0;\n" +
                    " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                    " 100%; background:white; margin:auto; text-align:left; max-width:600px;\n" +
                    " font-family: 'Asap', Helvetica, sans-serif;' text-align='left' width='100%'>\n" +
                    "                          <tr>\n" +
                    "                            <td style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%'>\n" +
                    "\n" +
                    "                              <h3 style='color: red; font-family: Asap, Helvetica, sans-serif;\n" +
                    " font-size: 20px; font-style: normal; font-weight: normal; line-height: 125%;\n" +
                    " letter-spacing: normal; text-align: center; display: block; margin: 0; padding:\n" +
                    " 0; text-align: left; width: 100%; font-size: 16px; font-weight: bold; '>\n" +
                    "Notice for payment of auction products!</h3>\n" +
                    "\n" +
                    "                              <p style='margin: 10px 0; padding: 0; mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color: #2a2a2a;\n" +
                    " font-family: Asap, Helvetica, sans-serif; font-size: 12px; line-height: 150%;\n" +
                    " text-align: left; text-align: left; font-size: 14px; '>\n" +
                    "Time to pay for successful auction products is 24 hours after receiving the first notice. Please make sure you have successfully paid for the product. In case you have not paid for the product within 24 hours, we will proceed to lock your account.\n" +
                    "                              </p>\n" +
                    "                            </td>\n" +
                    "                          </tr>\n" +
                    "                        </table>\n" +
                    "                        <table align='center' border='0' cellpadding='0' cellspacing='0' style='border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;' width='100%'>\n" +
                    "                          <tbody>\n" +
                    "                            <tr>\n" +
                    "                              <td style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%; padding-top: 24px; padding-right: 18px;\n" +
                    " padding-bottom: 24px; padding-left: 18px; color: #7F6925; font-family: Asap,\n" +
                    " Helvetica, sans-serif; font-size: 12px;' valign='top'>\n" +
                    "                                <div style='text-align: center;'>Made with\n" +
                    "                                  <a href='https://thenounproject.com/' style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration:\n" +
                    " none' target='_blank'>\n" +
                    "                                    <img align='none' alt='Heart icon' height='10' src='https://static.lingoapp.com/assets/images/email/made-with-heart.png' style='-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                    " text-decoration: none; width: 10px; height: 10px; margin: 0px;' width='10' />\n" +
                    "                                  </a>by\n" +
                    "                                  <a href='https://thenounproject.com/' style='mso-line-height-rule: exactly;\n" +
                    " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color: #f57153;\n" +
                    " font-weight: normal; text-decoration: none; color:#7F6925;' target='_blank' title='Noun Project - Icons for Everything'>A0721I1 Auction</a></div>\n" +
                    "                              </td>\n" +
                    "                            </tr>\n" +
                    "                            <tbody></tbody>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                        <table align='center' border='0' cellpadding='12' style='border-collapse:\n" +
                    " collapse; mso-table-lspace: 0; mso-table-rspace: 0; -ms-text-size-adjust:\n" +
                    " 100%; -webkit-text-size-adjust: 100%; '>\n" +
                    "                          <tbody>\n" +
                    "                            <tr>\n" +
                    "                              <td style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%'>\n" +
                    "                                <a href='https://twitter.com/@lingo_app' style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration: none' target='_blank'>\n" +
                    "                                  <img alt='twitter' height='32' src='https://static.lingoapp.com/assets/images/email/twitter-ic-32x32-email@2x.png' style='-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none; text-decoration:\n" +
                    " none' width='32' />\n" +
                    "                                </a>\n" +
                    "                              </td>\n" +
                    "                              <td style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%'>\n" +
                    "                                <a href='https://www.instagram.com/lingo_app/' style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration:\n" +
                    " none' target='_blank'>\n" +
                    "                                  <img alt='Instagram' height='32' src='https://static.lingoapp.com/assets/images/email/instagram-ic-32x32-email@2x.png' style='-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                    " text-decoration: none' width='32' />\n" +
                    "                                </a>\n" +
                    "                              </td>\n" +
                    "                              <td style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%'>\n" +
                    "                                <a href='https://medium.com/@lingo_app' style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration: none' target='_blank'>\n" +
                    "                                  <img alt='medium' height='32' src='https://static.lingoapp.com/assets/images/email/medium-ic-32x32-email@2x.png' style='-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none; text-decoration: none' width='32' />\n" +
                    "                                </a>\n" +
                    "                              </td>\n" +
                    "                              <td style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%'>\n" +
                    "                                <a href='https://www.facebook.com/Lingoapp/' style='mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                    " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration: none' target='_blank'>\n" +
                    "                                  <img alt='facebook' height='32' src='https://static.lingoapp.com/assets/images/email/facebook-ic-32x32-email@2x.png' style='-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                    " text-decoration: none' width='32' />\n" +
                    "                                </a>\n" +
                    "                              </td>\n" +
                    "                            </tr>\n" +
                    "                          </tbody>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "          </table>\n" +
                    "          <!--[if gte mso 9]>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            <![endif]-->\n" +
                    "          <!-- // END TEMPLATE -->\n" +
                    "        </td>\n" +
                    "      </tr>\n" +
                    "    </table>";
            helper.setText(content, true);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", 465, "a0721i1.2022@gmail.com", "ykddrsefedbcbvos");
            transport.send(msg);
            System.out.println("Send Email Successfully!!");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    //VinhTQ
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Product> findProductByIdForProductDetail(@PathVariable() long id) {
        return new ResponseEntity<Product>(productService.findProductByIdForProductDetail(id), HttpStatus.OK);
    }

    //VinhTQ
    @GetMapping("/highest-bidder/{id}")
    public ResponseEntity<Product> finForProductDetail(@PathVariable() long id) {
        return new ResponseEntity<Product>(productService.findProductByIdForProductDetail(id), HttpStatus.OK);
    }

    //HauLST
    @GetMapping("/list/auction")
    public ResponseEntity<List<Product>> showListProductAuction() {
        List<Product> productList = productService.getAllProductAuntion();
        if (productList.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        } else {
        }
        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }


    // HauLST
    @GetMapping("/list/finished-auction")
    public ResponseEntity<List<Product>> showListProductFinishedAuction() {
        List<Product> productList = productService.getAllProductFinishedAuntion();
        if (productList.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }

    //HauLST
    @GetMapping("/list/auction/{typeProduct}")
    public ResponseEntity<List<Product>> showListProductAuctionAndTypeProduct(@PathVariable String typeProduct) {
        List<Product> productList = productService.gettAllProductAuntionAndTypeProduct(typeProduct);
        if (productList.isEmpty()) {
            return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }

    //HauLST
    @GetMapping("list/search/name={nameProduct}/type-product={typeProduct}/{min}/{max}")
    public ResponseEntity<List<Product>> searchNameAndTypeAndPrices(@PathVariable String nameProduct, @PathVariable String typeProduct, @PathVariable Double min, @PathVariable Double max) {
        List<Product> productList = productService.searchProductByNameByTypeProductByPrice(nameProduct, typeProduct, min, max);
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    //ToanPT
    @GetMapping("/type")
    public ResponseEntity<List<TypeProduct>>  findByAllTypeProduct() {
        List<TypeProduct> typeProducts = typeProductService.findByAll();
        if (typeProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(typeProducts,  HttpStatus.OK);
        }
    }
    @GetMapping("/img")
    public ResponseEntity<List<ImageProduct>> findByAllImageProduct() {
        List<ImageProduct> imageProducts = imageProductService.findByAll();
        if (imageProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(imageProducts,HttpStatus.OK);
        }
    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id){
//        Product product = productService.getProductById(id);
//        return new ResponseEntity<>(product , HttpStatus.OK);
//
//    }

    @GetMapping("/member/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id){
        Optional<Member> member = memberService.findByIdMember(id);
        return new ResponseEntity<Member>(member.get() , HttpStatus.OK);
    }

    @PatchMapping("/edit")
    public ResponseEntity editProduct(@RequestBody Product product){
        productService.updateProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //HieuDV
    @GetMapping("list-bidding-status")
    public ResponseEntity<Iterable<BiddingStatus>> getAllBiddingStatus() {
        List<BiddingStatus> biddingStatusList = biddingStatusService.findByAll();
        if (biddingStatusList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(biddingStatusList,  HttpStatus.OK);
        }
    }

    //HauLST
    @GetMapping("list/search/name={nameProduct}/type-product={typeProduct}/{min}")
    public ResponseEntity<List<Product>> searchPricesOver250(@PathVariable String nameProduct, @PathVariable String typeProduct, @PathVariable Double min) {
        List<Product> productList = productService.searchProductPricesOver250(nameProduct, typeProduct, min);
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    //HieuDV
    @GetMapping("list-approval-status")
    public ResponseEntity<Iterable<ApprovalStatus>> getAllApprovalStatus() {
        List<ApprovalStatus> approvalStatusList = approvalStatusService.findAllBy();
        if (approvalStatusList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(approvalStatusList, HttpStatus.OK);
    }


    //HieuDV
    @GetMapping("/search/{name}/{typeProduct}/{sellerName}/{maxPrice}/{minPrice}/{biddingStatus}/{page}")
    public ResponseEntity<Iterable<Product>> getAllProductByNameTypeSellerPriceStatus(@PathVariable String name,
                                                                                      @PathVariable String typeProduct,
                                                                                      @PathVariable String sellerName,
                                                                                      @PathVariable String maxPrice,
                                                                                      @PathVariable String minPrice,
                                                                                      @PathVariable String biddingStatus,
                                                                                      @PathVariable int page) {
        if (name.equals("null")) {
            name = "";
        }
        if (typeProduct.equals("null")) {
            typeProduct = "";
        }
        if (sellerName.equals("null")) {
            sellerName = "";
        }
        if (maxPrice.equals("null")) {
            maxPrice = "10000000";
        }
        if (minPrice.equals("null")) {
            minPrice = "0";
        }
        if (biddingStatus.equals("null")) {
            biddingStatus = "";
        }

        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> productList = productService.getAllProductByNameTypeSellerPriceStatus(name, typeProduct, sellerName, maxPrice, minPrice, biddingStatus, pageable);
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    //HieuDV
    @GetMapping("/product-detail")
    public ResponseEntity<Product> getProductByIdProduct(@RequestParam Long id) {
        Optional<Product> product = this.productService.getProductByIdProduct(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    //HieuDV
    @PutMapping("/update-bidding-status")
    public ResponseEntity<Product> updateProductBiddingStatus(@RequestBody Product product) {
        this.productService.saveProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //thao
    @GetMapping( "listProduct")
    public ResponseEntity<List<Product>> findByAll() {
        List<Product> productList = productService.findAll();

        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }


    //Thao
    @PostMapping("postProduct")
    public ResponseEntity<Product> postProduct(@RequestBody Product product) throws ParseException {
        String timeFormatEndDate = product.getEndDate().replace ( "T" , " " );
        String timeFormatStartDate =product.getStartDate().replace ( "T" , " " );
        product.setStartDate(timeFormatStartDate);
        product.setEndDate(timeFormatEndDate);
        product.setBiddingStatus(this.biddingStatusService.findById((long) 2));
        product.setFlagDelete(false);
        product.setFinalPrice(product.getInitialPrice());
        List<ApprovalStatus> approvalStatusList = approvalStatusService.findAllBy();
        for (ApprovalStatus a : approvalStatusList) {
            if (a.getIdApprovalStatus() == 1) {
                /* Get approval status by id */
                ApprovalStatus approvalStatus = approvalStatusService.getApprovalStatusById(a.getIdApprovalStatus());
                product.setApprovalStatus(approvalStatus);
            }
        }
        Product productCreated = productService.postProduct(product);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }
    //Thao
    @GetMapping("/checkId")
    public ResponseEntity<List<Product>> checkId(@RequestParam String id) {
        List<Product> list = productService.findAll();
        List<Product> products = new ArrayList<>();
        for (Integer i = 0; i < list.size(); i++) {
            if (list.get(i).getCodeProduct().equals(id)) {
                products.add(list.get(i));
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/typeProduct")
    public ResponseEntity<List<TypeProduct>> findByAllTypeproduct() {
        List<TypeProduct> typeProducts = typeProductService.findByAll();
        if (typeProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(typeProducts, HttpStatus.OK);
        }
    }

    @PostMapping( "/create-images")
    public ResponseEntity<ImageProduct> createImages(@RequestBody ImageProduct imageProduct) {
        /* Save each picture */
        ImageProduct imageProduct1 = imageProductService.save(imageProduct);
        return new ResponseEntity<>(imageProduct1, HttpStatus.OK);
    }

    @GetMapping("/typeProduct/{id}")
    public ResponseEntity<TypeProduct> getTypeProductById(@PathVariable long id) {
        return new ResponseEntity<TypeProduct>(typeProductService.findById(id), HttpStatus.OK);
    }
}
