package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.ImageProduct;
import project2.model.Product;

import java.util.List;

@Repository
public interface IImageProductRepository extends JpaRepository<ImageProduct,Long> {
    public List<ImageProduct> findByProduct(Product product);
    public List<ImageProduct> findAllByProduct_IdProduct(Long idProduct);
}
