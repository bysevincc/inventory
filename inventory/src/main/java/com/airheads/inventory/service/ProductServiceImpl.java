package com.airheads.inventory.service;

import com.airheads.inventory.dto.ProductDto;
import com.airheads.inventory.entity.Category;
import com.airheads.inventory.entity.Product;
import com.airheads.inventory.exception.NoSuchElementFoundException;
import com.airheads.inventory.mapper.MapStructMapper;
import com.airheads.inventory.repository.CategoryRepository;
import com.airheads.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MapStructMapper mapper;


    @Override
    public void addProductToCategory(Long categoryId, String productName) {
        log.info("Add product {} to the category {}", categoryId, productName);
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new NoSuchElementFoundException("Category not found"));

        Product product=productRepository.findByProductName(productName)
                .orElseThrow(()->new NoSuchElementFoundException("Product name not found"));
        category.getProducts().add(product);
    }


    @Override
    public ProductDto getProductById(Long productId) {
        log.info("Fetching product {}", productId);
        return productRepository
                .findById(productId)
                .map(mapper::productToProductDto)
                .orElseThrow(()-> new NoSuchElementFoundException("Product not found"));
    }

    @Override
    public List<ProductDto> getProducts() {
        log.info("Fetching all products");
        return productRepository
                .findAll()
                .stream()
                .map(mapper::productToProductDto)
                .toList();
    }


    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        log.info("Save new product {} to database", productDto.getProductName());

        return mapper.productToProductDto(productRepository.save(mapper.productDtoToProduct(productDto)));
    }
}
