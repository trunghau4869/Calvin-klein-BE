package project2.service.impl;
import org.springframework.stereotype.Service;
import project2.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import project2.model.Payment;
import project2.repository.IPaymentRepository;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private IPaymentRepository iPaymentRepository;

    @Override
    public Payment save(Payment payment) {
        return iPaymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayment() {
        return iPaymentRepository.getAllPayment();
    }

    @Override
    public Payment getPaymentEnd() {
        return iPaymentRepository.getPaymentEnd();
    }
}
