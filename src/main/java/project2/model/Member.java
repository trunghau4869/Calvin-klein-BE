package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_member")
    private Long idMember;

    @Column(name = "name_member")
    @NotEmpty(message = "Name not empty")
    @Pattern(regexp = "^[A-Za-zỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđ' ]+$", message = "Name not contain character special")
    private String nameMember;

    @Column(name = "date_of_birth_member")
    @NotEmpty(message = "Date of birth not empty")
    private String dateOfBirthMember;

    @Column(name = "email_member")
    @NotEmpty(message = "Email not empty")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not empty")
    private String emailMember;

    @Column(name = "address_member")
    @NotEmpty(message = "Address not empty")
    private String addressMember;

    @Column(name = "phone_member")
    @NotEmpty(message = "Phone not empty")
    @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{9})$", message = "Phone number must correct format")
    private String phoneMember;

    @Column(name = "id_card_member")
    @NotEmpty(message = "Id card not empty")
    private String idCardMember;

    @Column(name = "paypal_member")
    private String paypalMember;

    @Column(name = "flag_delete")
    private Boolean flagDelete;

    @Column(name = "checked_clause")
    private Boolean checkedClause;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    private Account account;

    @OneToMany(mappedBy = "member")
    @JsonBackReference(value = "member_invoice")
    private List<Invoice> invoiceList;

    @Column(name = "point")
    private Double point = Double.valueOf(10);

    @OneToMany(mappedBy = "member")
    @JsonBackReference(value = "member_payment")
    private List<Payment> paymentList;

    @ManyToOne(targetEntity = Rank.class)
    @JoinColumn(name = "id_rank", nullable = false)
    private Rank rank;

    @OneToOne(mappedBy = "member")
    @JsonBackReference(value = "member_cart")
    private Cart cart;

    //    @JsonIgnore
//    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<Product> products;
    @OneToMany(mappedBy = "members")
    @JsonBackReference(value = "member_product")
    private List<Product> products;



    public Member(Long idMember, String nameMember, String dateOfBirthMember, String emailMember, String addressMember, String phoneMember, String idCardMember, String paypalMember, Boolean flagDelete, Boolean checkedClause, Account account, List<Invoice> invoiceList, Double point, List<Payment> paymentList, Rank rank, Cart cart, List<Product> products) {
        this.idMember = idMember;
        this.nameMember = nameMember;
        this.dateOfBirthMember = dateOfBirthMember;
        this.emailMember = emailMember;
        this.addressMember = addressMember;
        this.phoneMember = phoneMember;
        this.idCardMember = idCardMember;
        this.paypalMember = paypalMember;
        this.flagDelete = flagDelete;
        this.checkedClause = checkedClause;
        this.account = account;
        this.invoiceList = invoiceList;
        this.point = point;
        this.paymentList = paymentList;
        this.rank = rank;
        this.cart = cart;
        this.products = products;
    }

    public Member() {
    }

    public Long getIdMember() {
        return idMember;
    }

    public Boolean getCheckedClause() {
        return checkedClause;
    }


    public void setIdMember(Long idMember) {
        this.idMember = idMember;
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public String getDateOfBirthMember() {
        return dateOfBirthMember;
    }

    public void setDateOfBirthMember(String dateOfBirthMember) {
        this.dateOfBirthMember = dateOfBirthMember;
    }

    public String getEmailMember() {
        return emailMember;
    }

    public void setEmailMember(String emailMember) {
        this.emailMember = emailMember;
    }

    public String getAddressMember() {
        return addressMember;
    }

    public void setAddressMember(String addressMember) {
        this.addressMember = addressMember;
    }

    public String getPhoneMember() {
        return phoneMember;
    }

    public void setPhoneMember(String phoneMember) {
        this.phoneMember = phoneMember;
    }

    public String getIdCardMember() {
        return idCardMember;
    }

    public void setIdCardMember(String idCardMember) {
        this.idCardMember = idCardMember;
    }

    public String getPaypalMember() {
        return paypalMember;
    }

    public void setPaypalMember(String paypalMember) {
        this.paypalMember = paypalMember;
    }

    public Boolean getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Boolean flagDelete) {
        this.flagDelete = flagDelete;
    }


    public void setCheckedClause(Boolean checkedClause) {
        this.checkedClause = checkedClause;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}