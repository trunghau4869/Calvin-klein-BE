package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Long idInvoice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "status_invoice")
    private Boolean statusInvoice;

    @Column(name = "flag_delete")
    private Boolean flagDelete;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "id_member", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "invoice")
    @JsonBackReference(value = "invoice_invoiceDetail")
    private List<InvoiceDetail> invoiceDetailList;

    @ManyToOne(targetEntity = Payment.class)
    @JoinColumn(name = "id_payment",nullable = false)
    private Payment payment;

    public Invoice() {
    }

    public Invoice(Long idInvoice, Double totalPrice, LocalDate dateCreated, Boolean idStatusInvoice, Boolean flagDelete, Member member, List<InvoiceDetail> invoiceDetailList, Payment payment) {
        this.idInvoice = idInvoice;
        this.totalPrice = totalPrice;
        this.dateCreated = dateCreated;
        this.statusInvoice = idStatusInvoice;
        this.flagDelete = flagDelete;
        this.member = member;
        this.invoiceDetailList = invoiceDetailList;
        this.payment = payment;
    }

    public Long getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(Long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIdStatusInvoice() {
        return statusInvoice;
    }

    public void setIdStatusInvoice(Boolean idStatusInvoice) {
        this.statusInvoice = idStatusInvoice;
    }

    public Boolean getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Boolean flagDelete) {
        this.flagDelete = flagDelete;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<InvoiceDetail> getInvoiceDetailList() {
        return invoiceDetailList;
    }

    public void setInvoiceDetailList(List<InvoiceDetail> invoiceDetailList) {
        this.invoiceDetailList = invoiceDetailList;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
