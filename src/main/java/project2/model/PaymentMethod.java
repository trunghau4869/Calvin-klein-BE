package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment_method")
    private Long idPaymentMethod;

    @Column(name = "name_payment_method")
    private String namePaymentMethod;

    @OneToMany(mappedBy = "paymentMethod")
    @JsonBackReference(value = "paymentMethod_payment")
    private List<Payment> paymentList;

    public PaymentMethod() {
    }

    public PaymentMethod(Long idPaymentMethod, String namePaymentMethod, List<Payment> paymentList) {
        this.idPaymentMethod = idPaymentMethod;
        this.namePaymentMethod = namePaymentMethod;
        this.paymentList = paymentList;
    }

    public Long getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(Long idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public String getNamePaymentMethod() {
        return namePaymentMethod;
    }

    public void setNamePaymentMethod(String namePaymentMethod) {
        this.namePaymentMethod = namePaymentMethod;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }
}
