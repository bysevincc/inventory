package com.airheads.inventory.service;

import com.airheads.inventory.dto.CategoryDto;

import java.util.List;

public interface CategoryService {


    CategoryDto saveOrUpdateCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long categoryId);

    List<CategoryDto> getCategories();

    void deleteCategory(Long id);
}
