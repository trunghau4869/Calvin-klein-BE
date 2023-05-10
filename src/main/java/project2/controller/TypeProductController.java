package project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project2.service.ITypeProductService;

@RestController
@RequestMapping("/manager/type-product/api")
@CrossOrigin("*")
public class TypeProductController {

    @Autowired
    private ITypeProductService typeProductService;

    @GetMapping
    public ResponseEntity getTypeProduct() {
      return ResponseEntity.ok(this.typeProductService.findByAll());

    }
}