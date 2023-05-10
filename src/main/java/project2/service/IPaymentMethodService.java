package project2.service;

import com.paypal.api.payments.Payer;
import com.paypal.base.rest.PayPalRESTException;
import project2.model.Payment;
import project2.model.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface IPaymentMethodService {


    List<PaymentMethod> getAllPaymentMethod();

    Optional<PaymentMethod> getPaymentMethodById(Long id);




}
