package com.nipun.product.service.impl;

import com.nipun.product.dto.Product;
import com.nipun.product.entity.ProductEntity;
import com.nipun.product.repository.ProductRepository;
import com.nipun.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product createProduct(Product product) {
        ProductEntity entity = modelMapper.map(product, ProductEntity.class);
        return modelMapper.map(productRepository.save(entity), Product.class);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, Product.class))
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return modelMapper.map(entity, Product.class);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        entity.setProductCode(updatedProduct.getProductCode());
        entity.setName(updatedProduct.getName());
        entity.setPrice(updatedProduct.getPrice());

        return modelMapper.map(productRepository.save(entity), Product.class);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}