package com.airheads.inventory.controller;

import com.airheads.inventory.dto.AddProductToCategoryDto;
import com.airheads.inventory.dto.OnCreate;
import com.airheads.inventory.dto.OnUpdate;
import com.airheads.inventory.dto.ProductDto;
import com.airheads.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Validated(OnCreate.class)
    @PostMapping("/product")
    public  ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto ){

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand()
                .toUri();

        return ResponseEntity
                .created(location)
                .body(productService.saveProduct(productDto));
    }

    @PostMapping("/add-product-to-category")
    public ResponseEntity<Void> addProductToCategory(@RequestBody @Valid AddProductToCategoryDto requestDto) {
        productService.addProductToCategory(requestDto.getCategoryId(),requestDto.getProductName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> listOfProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
}

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) {

      productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @Validated(OnUpdate.class)
    @PutMapping("/product")
    public ResponseEntity<ProductDto> update(@RequestBody @Valid  ProductDto productDto)
    {
        return ResponseEntity.ok(productService.saveProduct(productDto));

    }

}