package project2.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import project2.model.Product;


import java.util.Optional;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IProductService {
    ResponseEntity save(Product product, Long idMember, List<MultipartFile> multipartFile);


    Product postProduct(Product product);

    List<Product> findAll();

    //QuangNV write method get product in cart
    List<Product> getProductInCart(int i);

    void saveListProduct(List<Product> productList);

    //HieuDV
    Page<Product> getAllNotDeletedYet(Pageable pageable);

    //HieuDV
    List<Product> getAllNotDeletedYet();

    //HieuDV
    Page<Product> getAllProductByNameTypeSellerPriceStatus(String name, String typeProduct, String sellerName, String maxPrice, String minPrice, String BiddingStatus, Pageable pageable);

    //HieuDV
    List<Product> getAllProductByNameTypeSellerPriceStatusNotPagination(String name, String typeProduct, String sellerName, String maxPrice, String minPrice, String BiddingStatus);

    //HieuDV
    Optional<Product> getProductByIdProduct(Long id);

    //HieuDV
    void saveProduct(Product product);

    //HieuDV
    void setFlagDeleteProduct(Long id);

    //BachLT
    List<Product> getAllProductByEndDate(String statsBegin, String statsEnd, int biddingStatus);

    //BachLT
    List<Product> getAllProductAtCurrentMonth(int curMonth, int biddingStatus);

    //ToanPT
    Product getProductById(Long id);
    //ToanPT
    Product updateProduct(Product product);

    //HuyNN
    void updateCurrentPrice(Product product);

    //HuyNN
    void updateIdCard(Product product);

    //VinhTQ
    Product findProductByIdForProductDetail(long id);

    //HauLST
    List<Product> getAllProductAuntion();

    //HauLST
    List<Product> getAllProductFinishedAuntion();

    //HauLST
    List<Product> gettAllProductAuntionAndTypeProduct(String nameTypeProduct);

    //HauLST
    List<Product> searchProductByNameByTypeProductByPrice(String nameProduct, String nameTypeProduct, Double min, Double max);

    //HauLST
    List<Product> searchProductPricesOver250(String nameProduct, String nameTypeProduct, Double min);
}
