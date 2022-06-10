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
public class CategoryDtoTest {

    @Test
    void valid_categoryDto_test() {

        CategoryDto validCategory = CategoryDto.builder()
                .categoryId(10L)
                .categoryName("Garden")
                .build();


        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(validCategory);

        assertThat(violations).isEmpty();
    }

    @Test
    void invalid_categoryDto_test() {
        CategoryDto validCategory = CategoryDto.builder()
                .categoryName("Garden")
                .build();


        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(validCategory);

        assertThat(violations).hasSize(0);
    }
}