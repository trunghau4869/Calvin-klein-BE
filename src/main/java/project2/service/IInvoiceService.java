package project2.service;
import org.springframework.stereotype.Service;

import project2.model.Invoice;

import java.util.List;

@Service
public interface IInvoiceService {


    void save(Invoice invoice);
}
