package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import project2.model.Invoice;
import project2.repository.IInvoiceRepository;
import project2.service.IInvoiceService;

import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {

    @Autowired
    private IInvoiceRepository iInvoiceRepository;
    @Override
    public void save(Invoice invoice) {
        iInvoiceRepository.save(invoice);
    }
}