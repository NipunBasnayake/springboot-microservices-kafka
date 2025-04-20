package com.product.product.service;

import com.product.product.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO saveProduct(ProductDTO product);

    ProductDTO updateProduct(ProductDTO product);

    String deleteProduct(Integer productId);

    ProductDTO getProductById(Integer productId);
}
