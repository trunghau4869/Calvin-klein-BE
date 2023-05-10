package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.model.Payment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project2.model.Payment;
import java.util.List;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "select * from payment", nativeQuery = true)
    List<Payment> getAllPayment();

    @Query(value = "SELECT * FROM payment ORDER BY id_payment DESC LIMIT 1", nativeQuery = true)
    Payment getPaymentEnd();
}
