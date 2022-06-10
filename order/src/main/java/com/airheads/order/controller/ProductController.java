package com.airheads.order.controller;

import com.airheads.order.entity.Product;
import com.airheads.order.service.ProductService;
import com.sun.istack.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {


    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = { "", "/" })
    public @NotNull
    Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }
}
