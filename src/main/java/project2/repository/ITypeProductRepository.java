package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.TypeProduct;

@Repository
public interface ITypeProductRepository extends JpaRepository<TypeProduct, Long> {
}
