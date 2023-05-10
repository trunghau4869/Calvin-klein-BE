package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.ImageProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project2.model.InvoiceDetail;
import project2.repository.IInvoiceDetailRepository;
import project2.service.IInvoiceDetailService;

import java.util.List;

@Service
public class InvoiceDetailService implements IInvoiceDetailService {

    @Autowired
    private IInvoiceDetailRepository iInvoiceDetailRepository;

    @Override
    public List<InvoiceDetail> findAllStatusInvoice(Long idPayment)  {
        return iInvoiceDetailRepository.findAllStatusInvoice(idPayment);
    }

    @Override
    public void saveList(List<InvoiceDetail> invoiceDetailList) {
        iInvoiceDetailRepository.saveAll(invoiceDetailList);
    }

//    @Override
//    public List<ImageProduct> findAllImageProduct(int id) {
//        return iInvoiceDetailRepository.findAllImg(id);
//    }

    @Override
    public Page<InvoiceDetail> getAll(Pageable pageable) {
        return iInvoiceDetailRepository.findAll(pageable);
    }
}
