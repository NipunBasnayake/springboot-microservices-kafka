package com.product.product.service.impl;

import com.product.product.dto.ProductDTO;
import com.product.product.model.Product;
import com.product.product.repo.ProductRepo;
import com.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found with id: ";

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepo.findById(productDTO.getId());
        if (existingProduct.isEmpty()) {
            throw new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE + productDTO.getId());
        }

        Product productToUpdate = modelMapper.map(productDTO, Product.class);
        Product updatedProduct = productRepo.save(productToUpdate);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public String deleteProduct(Integer productId) {
        if (!productRepo.existsById(productId)) {
            throw new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE + productId);
        }

        productRepo.deleteById(productId);
        return "Product deleted successfully";
    }

    @Override
    public ProductDTO getProductById(Integer productId) {
        Optional<Product> product = productRepo.findById(productId);
        return product.map(value -> modelMapper.map(value, ProductDTO.class))
                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND_MESSAGE + productId));
    }
}