package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    @Column(name = "code_product")
    private String codeProduct;
    @Column(name = "name_product")
    private String nameProduct;
    @Column(name = "initial_price")
    private Double initialPrice;
    @Column(name = "final_price")
    private Double finalPrice;
    @Column(name = "increment_price")
    private Double incrementPrice;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
    @Column(name = "remaining_time")
    private String remainingTime;
    @Column(name = "create_day")
    private String createDay;
    @Column(name = "flag_delete")
    private Boolean flagDelete;

    @ManyToOne(targetEntity = TypeProduct.class)
    @JoinColumn(name = "id_product_type", nullable = false)
    private TypeProduct typeProduct;

    @ManyToOne(targetEntity = ApprovalStatus.class)
    @JoinColumn(name = "id_approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

    @ManyToOne(targetEntity = BiddingStatus.class)
    @JoinColumn(name = "id_bidding_status", nullable = false)
    private BiddingStatus biddingStatus;

    @OneToMany(mappedBy = "product")
//    @JsonBackReference(value = "product_imageProduct")
    private List<ImageProduct> imageProductList;

    @OneToMany(mappedBy = "product")
    @JsonBackReference(value = "product_invoiceDetail")
    private List<InvoiceDetail> invoiceDetailList;

    @ManyToOne(targetEntity = Cart.class)
    @JoinColumn(name = "id_cart", nullable = true)
    private Cart cart;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "id_member", nullable = true)
    private Member members;

    public Product() {
    }

    public Product(Long idProduct, String codeProduct, String nameProduct, Double initialPrice, Double finalPrice, Double incrementPrice, String productDescription, String startDate, String endDate, String remainingTime, String createDay, Boolean flagDelete, TypeProduct typeProduct, ApprovalStatus approvalStatus, BiddingStatus biddingStatus, List<ImageProduct> imageProductList, List<InvoiceDetail> invoiceDetailList, Cart cart, Member member) {
        this.idProduct = idProduct;
        this.codeProduct = codeProduct;
        this.nameProduct = nameProduct;
        this.initialPrice = initialPrice;
        this.finalPrice = finalPrice;
        this.incrementPrice = incrementPrice;
        this.productDescription = productDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingTime = remainingTime;
        this.createDay = createDay;
        this.flagDelete = flagDelete;
        this.typeProduct = typeProduct;
        this.approvalStatus = approvalStatus;
        this.biddingStatus = biddingStatus;
        this.imageProductList = imageProductList;
        this.invoiceDetailList = invoiceDetailList;
        this.cart = cart;
        this.members = member;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getIncrementPrice() {
        return incrementPrice;
    }

    public void setIncrementPrice(Double incrementPrice) {
        this.incrementPrice = incrementPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }

    public Boolean getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Boolean flagDelete) {
        this.flagDelete = flagDelete;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public BiddingStatus getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(BiddingStatus biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public List<ImageProduct> getImageProductList() {
        return imageProductList;
    }

    public void setImageProductList(List<ImageProduct> imageProductList) {
        this.imageProductList = imageProductList;
    }

    public List<InvoiceDetail> getInvoiceDetailList() {
        return invoiceDetailList;
    }

    public void setInvoiceDetailList(List<InvoiceDetail> invoiceDetailList) {
        this.invoiceDetailList = invoiceDetailList;
    }

    public Member getMember() {
        return members;
    }

    public void setMember(Member member) {
        this.members = member;
    }


    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}


