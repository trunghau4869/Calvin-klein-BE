package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image_product")
    private Long idImageProduct;
    @Column(name = "image_product")
    private String imageProduct;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "id_product", nullable = false)
    @JsonBackReference(value = "imageProduct_product")
    private Product product;

    public ImageProduct() {
    }

    public ImageProduct(String imageProduct) {
        this(null, imageProduct, null);
    }

    public ImageProduct(Long idImageProduct, String imageProduct, Product product) {
        this.idImageProduct = idImageProduct;
        this.imageProduct = imageProduct;
        this.product = product;
    }

    public Long getIdImageProduct() {
        return idImageProduct;
    }

    public void setIdImageProduct(Long idImageProduct) {
        this.idImageProduct = idImageProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
