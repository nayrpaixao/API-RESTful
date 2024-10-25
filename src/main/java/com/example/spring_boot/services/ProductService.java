package com.example.spring_boot.services;

import com.example.spring_boot.controllers.ProductController;
import com.example.spring_boot.dtos.ProductRecordDto;
import com.example.spring_boot.models.ProductModel;
import com.example.spring_boot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel saveProduct(ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return productRepository.save(productModel);
    }


    public List<ProductModel> getAllProducts() {
        {
            return productRepository.findAll();
        }

    }
    public Optional<ProductModel> getOneProduct(@PathVariable(value="id")UUID id) {
       return productRepository.findById(id);

    }

    public Optional<ProductModel> updateProduct(UUID id, ProductRecordDto productRecordDto) {
        Optional<ProductModel> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            var productModel = productOptional.get();
            BeanUtils.copyProperties(productRecordDto, productModel, "idProduct");
            productRepository.save(productModel);
        }
        return productOptional;
    }

    public boolean deleteProduct(UUID id) {
        Optional<ProductModel> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            var productModel = productOptional.get();
            productModel.setActive(false); // Marcando como inativo
            productRepository.save(productModel); // Salvando a alteração no banco
            return true;
        }
        return false;
    }
}




