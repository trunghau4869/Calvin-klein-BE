package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class BiddingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bidding_status")
    private Long idBiddingStatus;
    @Column(name = "name_bidding_status")
    private String nameBiddingStatus;

    @OneToMany(mappedBy = "biddingStatus")
    @JsonBackReference(value = "biddingStatus_product")
    private List<Product> products;

    public BiddingStatus() {
    }

    public BiddingStatus(Long idBiddingStatus, String nameBiddingStatus, List<Product> products) {
        this.idBiddingStatus = idBiddingStatus;
        this.nameBiddingStatus = nameBiddingStatus;
        this.products = products;
    }

    public Long getIdBiddingStatus() {
        return idBiddingStatus;
    }

    public void setIdBiddingStatus(Long idBiddingStatus) {
        this.idBiddingStatus = idBiddingStatus;
    }

    public String getNameBiddingStatus() {
        return nameBiddingStatus;
    }

    public void setNameBiddingStatus(String nameBiddingStatus) {
        this.nameBiddingStatus = nameBiddingStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
