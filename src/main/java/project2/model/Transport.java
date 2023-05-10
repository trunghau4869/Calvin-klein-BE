package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transport")
    private Long idTransport;

    @Column(name = "name_transport")
    private String nameTransport;

    @Column(name = "fee_transport")
    private Double feeTransport;

    @OneToMany(mappedBy = "transport")
    @JsonBackReference(value = "transport_payment")
    private List<Payment> paymentList;

    public Transport() {
    }

    public Transport(Long idTransport, String nameTransport, Double feeTransport, List<Payment> paymentList) {
        this.idTransport = idTransport;
        this.nameTransport = nameTransport;
        this.feeTransport = feeTransport;
        this.paymentList = paymentList;
    }

    public Long getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Long idTransport) {
        this.idTransport = idTransport;
    }

    public String getNameTransport() {
        return nameTransport;
    }

    public void setNameTransport(String nameTransport) {
        this.nameTransport = nameTransport;
    }

    public Double getFeeTransport() {
        return feeTransport;
    }

    public void setFeeTransport(Double feeTransport) {
        this.feeTransport = feeTransport;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }
}
