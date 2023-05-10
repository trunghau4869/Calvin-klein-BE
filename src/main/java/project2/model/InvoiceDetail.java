package project2.model;

import javax.persistence.*;

@Entity
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice_detail")
    private Long idInvoiceDetail;

    @ManyToOne
    @JoinColumn(name = "id_invoice",nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "id_product",nullable = false)
    private Product product;

    public InvoiceDetail() {
    }


    public Long getIdInvoiceDetail() {
        return idInvoiceDetail;
    }

    public void setIdInvoiceDetail(Long idInvoiceDetail) {
        this.idInvoiceDetail = idInvoiceDetail;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
