package com.nipun.product.service;

import com.nipun.product.dto.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product updatedProduct);
    void deleteProduct(Long id);
}
