package com.airheads.inventory.service;

import com.airheads.inventory.dto.ProductDto;

import java.util.List;

public interface ProductService {

    void addProductToCategory(Long categoryId, String productName);

    ProductDto getProductById(Long productId);

    List<ProductDto> getProducts();

    void deleteProduct(Long id);

    ProductDto saveProduct(ProductDto productDto);
}
