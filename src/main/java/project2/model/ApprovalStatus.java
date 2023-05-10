package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class ApprovalStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_approval_status")
    private Long idApprovalStatus;
    @Column(name = "name_approval_status")
    private String nameApprovalStatus;

    @OneToMany(mappedBy = "approvalStatus")
    @JsonBackReference(value = "approvalStatus_product")
    private List<Product> products;

    public ApprovalStatus() {
    }

    public ApprovalStatus(Long idApprovalStatus, String nameApprovalStatus, List<Product> products) {
        this.idApprovalStatus = idApprovalStatus;
        this.nameApprovalStatus = nameApprovalStatus;
        this.products = products;
    }

    public Long getIdApprovalStatus() {
        return idApprovalStatus;
    }

    public void setIdApprovalStatus(Long idApprovalStatus) {
        this.idApprovalStatus = idApprovalStatus;
    }

    public String getNameApprovalStatus() {
        return nameApprovalStatus;
    }

    public void setNameApprovalStatus(String nameApprovalStatus) {
        this.nameApprovalStatus = nameApprovalStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
