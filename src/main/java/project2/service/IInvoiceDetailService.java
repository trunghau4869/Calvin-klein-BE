package project2.service;

import project2.model.ImageProduct;
import project2.model.InvoiceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IInvoiceDetailService {
    List<InvoiceDetail> findAllStatusInvoice(Long idPayment);

    void saveList(List<InvoiceDetail> invoiceDetailList);
//    List<ImageProduct> findAllImageProduct(int id);
    Page<InvoiceDetail> getAll(Pageable pageable);
}
