package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class TypeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_type")
    private Long idProductType;
    @Column(name = "name_product_type")
    private String nameProductType;

    @OneToMany(mappedBy = "typeProduct")
    @JsonBackReference(value = "typeProduct_product")
    private List<Product> products;

    public TypeProduct() {
    }

    public TypeProduct(Long idProductType, String nameProductType, List<Product> products) {
        this.idProductType = idProductType;
        this.nameProductType = nameProductType;
        this.products = products;
    }

    public Long getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(Long idProductType) {
        this.idProductType = idProductType;
    }

    public String getNameProductType() {
        return nameProductType;
    }

    public void setNameProductType(String nameProductType) {
        this.nameProductType = nameProductType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
