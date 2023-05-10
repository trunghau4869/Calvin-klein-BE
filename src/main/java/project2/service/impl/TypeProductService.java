package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project2.model.TypeProduct;
import project2.repository.ITypeProductRepository;
import project2.service.ITypeProductService;

import javax.persistence.Access;
import java.util.List;

import java.util.List;

@Service
public class TypeProductService implements ITypeProductService {
    @Autowired
    private ITypeProductRepository iTypeProductRepository;

    @Override
    public List<TypeProduct> findByAll() {
        return iTypeProductRepository.findAll();
    }

    @Override
    public TypeProduct findById(Long id) {
        return iTypeProductRepository.findById(id).orElse(null);
    }
}
