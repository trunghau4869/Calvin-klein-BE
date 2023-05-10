package project2.service;

import project2.model.TypeProduct;

import java.util.List;

public interface ITypeProductService {
    List<TypeProduct>findByAll();
    TypeProduct findById(Long id);

}
