package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Long idCart;

    @Column(name = "warning_date")
    private String warningDate;

    @Column(name = "warning")
    private String warning;

    @OneToMany(mappedBy = "cart")
    @JsonBackReference(value = "cart_payment")
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "cart")
    @JsonBackReference(value = "cart_product")
    private List<Product> productList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_member", referencedColumnName = "id_member")
    @JsonBackReference(value = "cart_member")
    private Member member;

    public Cart() {
    }

    public Cart(Long idCart, String warningDate, String warning, List<Payment> paymentList, List<Product> productList, Member member) {
        this.idCart = idCart;
        this.warningDate = warningDate;
        this.warning = warning;
        this.paymentList = paymentList;
        this.productList = productList;
        this.member = member;
    }

    public Cart(Long idCart) {
        this.idCart = idCart;
    }

    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long idCart) {
        this.idCart = idCart;
    }

    public String getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(String warningDate) {
        this.warningDate = warningDate;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
