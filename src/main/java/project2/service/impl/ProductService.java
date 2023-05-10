package project2.service.impl;


import com.google.type.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project2.model.*;
import project2.repository.*;
import project2.service.IProductService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private IImageProductRepository imageProductRepository;

    @Autowired
    private IApprovalStatusRepository approvalStatusRepository;

    @Autowired
    private IBiddingStatusRepository biddingStatusRepository;

    @Autowired

    private ICartRepository cartRepository;


    public ResponseEntity save(Product product, Long idMember, List<MultipartFile> multipartFile) {
        try {
            List<ApprovalStatus> all = this.approvalStatusRepository.findAll();
            List<BiddingStatus> all1 = this.biddingStatusRepository.findAll();
            String timeFormatEndDate = product.getEndDate().replace ( "T" , " " );
            String timeFormatStartDate =product.getStartDate().replace ( "T" , " " );
            product.setStartDate(timeFormatStartDate);
            product.setEndDate(timeFormatEndDate);
            Cart byMember = this.cartRepository.getByIdMember(idMember);

            Optional<Product> productOPT = this.productRepository.findByCodeProduct(product.getCodeProduct());
            if (productOPT.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product code existed");
            }
            product.setCreateDay(product.getCreateDay());
            product.setFinalPrice(product.getInitialPrice());
            product.setCreateDay(LocalDate.now().toString());
            product.setFlagDelete(false);
            product.setBiddingStatus(all1.get(1));
            product.setApprovalStatus(all.get(0));
            product.setCart(byMember);
            product.setMember(memberRepository.findById(idMember).get());
            List<ImageProduct> imageProducts = multipartFile
                    .stream()
                    .map(this.firebaseService::uploadFile)
                    .filter(Objects::nonNull)
                    .map(ImageProduct::new)
                    .collect(Collectors.toList());
            product.setImageProductList(imageProducts);
            Product save = this.productRepository.save(product);
            imageProducts.forEach(image -> image.setProduct(save));
            this.imageProductRepository.saveAll(imageProducts);
            return ResponseEntity.ok(save);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // QuangNV write method get product in cart
    @Override
    public List<Product> getProductInCart(int i) {
        return productRepository.getProductInCart(i);
    }

    @Override
    public void saveListProduct(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    @Override
    public List<Product> getAllProductByEndDate(String statsBegin, String statsEnd, int biddingStatus) {
        System.out.println(productRepository.findProductByEndDateAndBiddingStatus(statsBegin, statsEnd, biddingStatus));
        return productRepository.findProductByEndDateAndBiddingStatus(statsBegin, statsEnd, biddingStatus);
    }

    //BachLT
    @Override
    public List<Product> getAllProductAtCurrentMonth(int curMonth, int biddingStatus) {
        System.out.println(productRepository.findProductByCurrentMonthAndBiddingStatus(curMonth, biddingStatus));
        return productRepository.findProductByCurrentMonthAndBiddingStatus(curMonth, biddingStatus);
    }

    //Thao
    @Override
    public Product postProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id_product) {
        return productRepository.findById(id_product).orElse(null);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    //HieuDV
    @Override
    public Page<Product> getAllNotDeletedYet(Pageable pageable) {
        return productRepository.findAllNotDeletedYet(pageable);
    }

    //HieuDV
    @Override
    public List<Product> getAllNotDeletedYet() {
        return productRepository.findAllNotDeletedYet();
    }

    //HieuDV
    @Override
    public Page<Product> getAllProductByNameTypeSellerPriceStatus(String name, String typeProduct, String sellerName, String maxPrice, String minPrice, String BiddingStatus, Pageable pageable) {
        return productRepository.findAllProductByNameTypeSellerPriceStatus(name, typeProduct, sellerName, maxPrice, minPrice, BiddingStatus, pageable);
    }

    //HieuDV
    @Override
    public List<Product> getAllProductByNameTypeSellerPriceStatusNotPagination(String name, String typeProduct, String sellerName, String maxPrice, String minPrice, String BiddingStatus) {
        return productRepository.findAllProductByNameTypeSellerPriceStatus(name, typeProduct, sellerName, maxPrice, minPrice, BiddingStatus);
    }

    //HieuDV
    @Override
    public Optional<Product> getProductByIdProduct(Long id) {
        return productRepository.findById(id);
    }

    //HieuDV
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void setFlagDeleteProduct(Long id) {
        productRepository.setFlagDeleteProduct(id);
    }


    //HuyNN
    @Override
    public void updateCurrentPrice(Product product) {
        productRepository.save(product);
    }

    //HuyNN
    @Override
    public void updateIdCard(Product product) {
        productRepository.save(product);
    }

    //VinhTQ
    @Override
    public Product findProductByIdForProductDetail(long id) {
        return productRepository.findProductByIdForProductDetail(id);
    }

    //HauLST
    @Override
    public List<Product> getAllProductAuntion() {
        return productRepository.findAllProductAuction();
    }

    //HauLST
    @Override
    public List<Product> getAllProductFinishedAuntion() {
        return productRepository.findAllProductFinishedAuction();
    }

    //HauLST
    @Override
    public List<Product> gettAllProductAuntionAndTypeProduct(String nameTypeProduct) {
        return productRepository.gettAllProductAuntionAndTypeProduct(nameTypeProduct);
    }

    //HauLST
    @Override
    public List<Product> searchProductByNameByTypeProductByPrice(String nameProduct, String nameTypeProduct, Double
            min, Double max) {
        return productRepository.searchProductByNameByTypeProductByPrice(nameProduct, nameTypeProduct, min, max);
    }

    //HauLST
    @Override
    public List<Product> searchProductPricesOver250(String nameProduct, String nameTypeProduct, Double min) {
        return productRepository.searchProductPricesOver250(nameProduct, nameTypeProduct, min);
    }
}
