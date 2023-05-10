package project2.service;

import project2.model.ImageProduct;
import project2.model.Product;


import java.util.List;

public interface IImageProductService {

    ImageProduct save(ImageProduct imageProduct);

    List<ImageProduct> findByAll();

    public List<ImageProduct> findByProduct(Product product);

    //HuyNN
    public List<ImageProduct> findAllByProduct_IdProduct(Long idProduct);
}
