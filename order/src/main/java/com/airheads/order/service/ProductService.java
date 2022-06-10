package com.airheads.order.service;

import com.airheads.order.entity.Product;
import com.sun.istack.NotNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Validated
public interface ProductService {
    @NotNull
    Iterable<Product> getAllProducts();

    Product getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

    Product save(Product product);
}
