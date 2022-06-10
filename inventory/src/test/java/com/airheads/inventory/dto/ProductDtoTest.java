package com.airheads.inventory.dto;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductDtoTest {


    @Test
    void valid_productDto_test() {

        ProductDto validProduct = ProductDto.builder()
                .productId(5L)
                .productName("Laptop")
                .productType("Machine")
                .productSellingPrice(100L)
                .description("16 RAM")
                .vendor("HP")
                .weight("2")
                .weightUnit("KG")
                .barcode("TEST")
                .tag("It")
                .build();


        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(validProduct);

        assertThat(violations).isEmpty();
    }

    @Test
    void invalid_productDto_test() {
        ProductDto validProduct = ProductDto.builder()
                .productName("Laptop")
                .productType("Machine")
                .productSellingPrice(100L)
                .description("16 RAM")
                .vendor("HP")
                .weight("2")
                .weightUnit("KG")
                .barcode("")
                .tag("It")
                .build();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(validProduct);

        assertThat(violations).hasSize(0);
    }

}