package project2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project2.model.InvoiceDetail;


import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IInvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

    //Nhung
//    @Query(value ="SELECT   invoicedetail.id_invoice,invoice.date_created,paymentmethod.id_payment_method,transport.id_transport,imageproduct.id_product,invoicedetail.id_product ,invoice.id_payment, paymentmethod.name_payment_method,transport.name_transport,transport.fee_transport,invoicedetail.id_invoice_detail,payment.full_name_receiver,payment.phone_receiver,payment.email_receiver,payment.address_receiver,product.name_product,imageproduct.id_product,imageproduct.image_product,product.final_price " +
//            "FROM invoicedetail inner JOIN invoice " +
//            "ON invoicedetail.id_invoice = `invoice`.id_invoice " +
//            "inner join payment " +
//            "on invoice.id_payment=payment.id_payment " +
//            "inner join paymentmethod " +
//            "on payment.id_payment_method=paymentmethod.id_payment_method " +
//            "inner join transport " +
//            "on payment.id_transport=transport.id_transport " +
//            "inner join product " +
//            "on invoicedetail.id_product=product.id_product " +
//            "inner join imageproduct " +
//            "on product.id_product=imageproduct.id_product where payment.id_payment = ?1", nativeQuery = true)

    @Query(value ="SELECT invoicedetail.id_invoice,invoice.date_created,paymentmethod.id_payment_method,transport.id_transport,invoicedetail.id_product ,invoice.id_payment, paymentmethod.name_payment_method,transport.name_transport,transport.fee_transport,invoicedetail.id_invoice_detail,payment.full_name_receiver,payment.phone_receiver,payment.email_receiver,payment.address_receiver,product.name_product,product.final_price\n" +
            "            FROM invoicedetail inner JOIN invoice\n" +
            "            ON invoicedetail.id_invoice = `invoice`.id_invoice\n" +
            "            inner join payment\n" +
            "            on invoice.id_payment=payment.id_payment\n" +
            "            inner join paymentmethod\n" +
            "            on payment.id_payment_method=paymentmethod.id_payment_method\n" +
            "            inner join transport\n" +
            "            on payment.id_transport=transport.id_transport\n" +
            "            inner join product\n" +
            "            on invoicedetail.id_product=product.id_product where payment.id_payment = ?1", nativeQuery = true)
    List<InvoiceDetail> findAllStatusInvoice(Long idPayment);

    //Nhung
//    @Query(value="SELECT * from ImageProduct where id")

    //Huy-NCQ FindAll Transaction không có phân trang
    @Query(value = "select invoice.id_invoice, end_date, invoicedetail.id_invoice_detail, invoice.flag_delete, payment.full_name_receiver, invoice.id_payment, invoice.id_member, invoicedetail.id_product, invoice.status_invoice, `member`.name_member,final_price, initial_price, payment.fee_service from product \n" +
            "\tinner join invoicedetail\t \n" +
            "\ton invoicedetail.id_product = product.id_product\n" +
            "\tinner join invoice\n" +
            "\ton invoice.id_invoice = invoicedetail.id_invoice\n" +
            "    inner join payment\n" +
            "    on invoice.id_payment = payment.id_payment\n" +
            "    inner join `member`\n" +
            "    on invoice.id_member = `member`.id_member " +
            "where invoice.flag_delete = 0;", nativeQuery = true)
    List<InvoiceDetail> findAllTransactionNotPagination();

    //Huy-NCQ FindAll Transaction có phân trang
    @Query(value = "select invoice.id_invoice, end_date, invoicedetail.id_invoice_detail, invoice.flag_delete, payment.full_name_receiver, invoice.id_payment, invoice.id_member, invoicedetail.id_product, invoice.status_invoice, `member`.name_member,final_price, initial_price, payment.fee_service from product \n" +
            "\tinner join invoicedetail\t \n" +
            "\ton invoicedetail.id_product = product.id_product\n" +
            "\tinner join invoice\n" +
            "\ton invoice.id_invoice = invoicedetail.id_invoice\n" +
            "    inner join payment\n" +
            "    on invoice.id_payment = payment.id_payment\n" +
            "    inner join `member`\n" +
            "    on invoice.id_member = `member`.id_member " +
            "where invoice.flag_delete = 0 " +
            "order by (end_date) desc ",
            countQuery = "select invoice.id_invoice, end_date, invoicedetail.id_invoice_detail, invoice.flag_delete, payment.full_name_receiver, invoice.id_payment, invoice.id_member, invoicedetail.id_product, invoice.status_invoice, `member`.name_member,final_price, initial_price, payment.fee_service from product \n" +
                    "\tinner join invoicedetail\t \n" +
                    "\ton invoicedetail.id_product = product.id_product\n" +
                    "\tinner join invoice\n" +
                    "\ton invoice.id_invoice = invoicedetail.id_invoice\n" +
                    "    inner join payment\n" +
                    "    on invoice.id_payment = payment.id_payment\n" +
                    "    inner join `member`\n" +
                    "    on invoice.id_member = `member`.id_member " +
                    "where invoice.flag_delete = 0 "  +
                    "order by (end_date) desc ", nativeQuery = true)
    Page<InvoiceDetail> findAllTransaction(Pageable pageable);

    // Huy-NCQ Search Seller, Buyer, NameProduct và Status
    @Query(value = "select invoice.id_invoice, end_date, invoicedetail.id_invoice_detail, invoice.flag_delete, payment.full_name_receiver, invoice.id_payment, invoice.id_member, invoicedetail.id_product, invoice.status_invoice, `member`.name_member,final_price, initial_price, payment.fee_service  from product  " +
            "            inner join `invoicedetail` " +
            "            on `invoicedetail`.id_product = product.id_product " +
            "            inner join `invoice` " +
            "            on `invoice`.id_invoice = `invoicedetail`.id_invoice " +
            "            inner join `payment` " +
            "            on `invoice`.id_payment = `payment`.id_payment " +
            "            inner join `member` " +
            "            on `invoice`.id_member = `member`.id_member " +
            "            where `invoice`.flag_delete = 0 " +
            "            and name_member like concat('%', ?1, '%')" +
            "            and full_name_receiver like concat('%', ?2, '%')" +
            "            and name_product like concat('%', ?3, '%') " +
            "            and invoice.status_invoice = ?4 " +
            "            order by (end_date) desc ",
            countQuery = "select invoice.id_invoice, end_date, invoicedetail.id_invoice_detail, invoice.flag_delete, payment.full_name_receiver, invoice.id_payment, invoice.id_member, invoicedetail.id_product, invoice.status_invoice, `member`.name_member,final_price, initial_price, payment.fee_service  from product  " +
            "            inner join `invoicedetail` " +
            "            on `invoicedetail`.id_product = product.id_product " +
            "            inner join `invoice` " +
            "            on `invoice`.id_invoice = `invoicedetail`.id_invoice " +
            "            inner join `payment` " +
            "            on `invoice`.id_payment = `payment`.id_payment " +
            "            inner join `member` " +
            "            on `invoice`.id_member = `member`.id_member " +
            "            where `invoice`.flag_delete = 0 " +
            "            and name_member like concat('%', ?1, '%')" +
            "            and full_name_receiver like concat('%', ?2, '%')" +
            "            and name_product like concat('%', ?3, '%') " +
                    "    and invoice.status_invoice = ?4 "+
                    "    order by (end_date) desc ", nativeQuery = true)
    Page<InvoiceDetail> searchTransaction(String nameSeller, String nameBuyer, String nameProduct, String status, Pageable pageable);

    /* Huy-NCQ search date */
    @Query(value = "select invoice.id_invoice, end_date, invoicedetail.id_invoice_detail, invoice.flag_delete, payment.full_name_receiver, invoice.id_payment, invoice.id_member, invoicedetail.id_product, invoice.status_invoice, `member`.name_member,final_price, initial_price, payment.fee_service from product " +
            "            inner join invoicedetail " +
            "            on invoicedetail.id_product = product.id_product " +
            "            inner join invoice " +
            "            on invoice.id_invoice = invoicedetail.id_invoice " +
            "            inner join payment " +
            "            on invoice.id_payment = payment.id_payment " +
            "            inner join `member` " +
            "            on invoice.id_member = `member`.id_member " +
            "            where invoice.flag_delete = 0 " +
            "            and DATE(product.end_date) between ?1 and ?2 " +
            "            order by (end_date) desc ",
            countQuery = "select invoice.id_invoice, end_date, invoicedetail.id_invoice_detail, invoice.flag_delete, payment.full_name_receiver, invoice.id_payment, invoice.id_member, invoicedetail.id_product, invoice.status_invoice, `member`.name_member,final_price, initial_price, payment.fee_service from product\n" +
                    "            inner join invoicedetail " +
                    "            on invoicedetail.id_product = product.id_product " +
                    "            inner join invoice\n" +
                    "            on invoice.id_invoice = invoicedetail.id_invoice " +
                    "            inner join payment " +
                    "            on invoice.id_payment = payment.id_payment " +
                    "            inner join `member` " +
                    "            on invoice.id_member = `member`.id_member " +
                    "            where invoice.flag_delete = 0 " +
                    "            and DATE(product.end_date) between ?1 and ?2 "+
                    "            order by (end_date) desc ", nativeQuery = true)
    Page<InvoiceDetail> searchDate(String startDate, String endDate, Pageable pageable);

//    @Modifying
//    @Query(value = "UPDATE `project2`.`invoice` SET `invoice`.flag_delete = true WHERE Date(end_date) < NOW() - INTERVAL 30 DAY);", nativeQuery = true)
//    void setFlagDeleteAfter30Days();

    // Huy-NCQ ẩn record flag_delete
    @Modifying
    @Transactional
    @Query(value = "UPDATE `project2`.`invoice` SET `invoice`.flag_delete = true WHERE (`invoice`.id_invoice = ?1);", nativeQuery = true)
    void setStatus(Long id);
}
