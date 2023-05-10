package project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.model.InvoiceDetail;
import project2.repository.IInvoiceDetailRepository;
import project2.repository.IInvoiceRepository;
import project2.service.impl.InvoiceService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/manager/api")
public class TransactionController {

    @Autowired
    private IInvoiceDetailRepository iInvoiceDetailRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    IInvoiceRepository invoiceRepository;

    @GetMapping("/transaction-list-notPagination")
    public ResponseEntity<List<InvoiceDetail>> listTransaction() {

        List<InvoiceDetail> invoiceDetail = iInvoiceDetailRepository.findAllTransactionNotPagination();

        if (invoiceDetail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invoiceDetail, HttpStatus.OK);
    }

    @GetMapping("/transaction-list")
    public ResponseEntity<Page<InvoiceDetail>> listTransaction(@RequestParam(name = "index", required = false, defaultValue = "0") Integer index)  {

        Pageable pageable = PageRequest.of(index,5);
        Page<InvoiceDetail> invoiceDetail = iInvoiceDetailRepository.findAllTransaction(pageable);
        if (invoiceDetail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Page<InvoiceDetail>>(invoiceDetail, HttpStatus.OK);
    }

    @GetMapping("/search/{nameSeller}/{nameBuyer}/{nameProduct}")
    public ResponseEntity<Page<InvoiceDetail>> search(@PathVariable String nameSeller,
                                                      @PathVariable String nameBuyer,
                                                      @PathVariable String nameProduct,
                                                      @RequestParam("status") String status,
                                                      @PageableDefault(size = 5) Pageable pageable) {

        if (nameSeller.equals("null")) {
            nameSeller = "";
        }
        if (nameBuyer.equals("null")) {
            nameBuyer = "";
        }
        if (nameProduct.equals("null")) {
            nameProduct = "";
        }
        if (status.equals("null")) {
            status = "1";
        }else if (status.equals("false")) {
            status = "0";
        }else {
            status = "1";
        }

        Page<InvoiceDetail> invoiceDetails = iInvoiceDetailRepository.searchTransaction(nameSeller, nameBuyer, nameProduct, status, pageable);

        if (invoiceDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(invoiceDetails, HttpStatus.OK);
    }

    @GetMapping("/search-date/{startDate}/{endDate}")
    public ResponseEntity<Page<InvoiceDetail>> findByDate(@PathVariable("startDate") String startDate,
                                                          @PathVariable("endDate") String endDate,
                                                          @PageableDefault(size = 5) Pageable pageable) {


        Page<InvoiceDetail> invoiceDetails = iInvoiceDetailRepository.searchDate(startDate, endDate, pageable);
        if (invoiceDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(invoiceDetails, HttpStatus.OK);
    }

    @PatchMapping("/delete/{idInvoice}")
    public ResponseEntity<InvoiceDetail> delete(@PathVariable Long idInvoice) {
        iInvoiceDetailRepository.setStatus(idInvoice);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//
//    @PatchMapping
//    public ResponseEntity<InvoiceDetail> deleteAfter30Days() {
//        iInvoiceDetailRepository.setFlagDeleteAfter30Days();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
