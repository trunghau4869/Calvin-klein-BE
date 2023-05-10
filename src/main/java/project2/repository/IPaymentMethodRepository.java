package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.PaymentMethod;

@Repository
public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

}
