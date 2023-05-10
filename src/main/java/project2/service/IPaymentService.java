package project2.service;

import project2.model.Payment;

import java.util.List;

public interface IPaymentService {
    Payment save(Payment payment);

    List<Payment> getAllPayment();

    Payment getPaymentEnd();
}
