package com.airheads.inventory.service;

import com.airheads.inventory.dto.CategoryDto;
import com.airheads.inventory.entity.Category;
import com.airheads.inventory.exception.NoSuchElementFoundException;
import com.airheads.inventory.mapper.MapStructMapper;
import com.airheads.inventory.repository.CategoryRepository;
import com.airheads.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final MapStructMapper mapper;

    @Override
    public CategoryDto saveOrUpdateCategory(CategoryDto categoryDto) {
        log.info("Save/Update category {}", categoryDto.getCategoryId());
        categoryDto.setCategoryId(categoryDto.getCategoryId());
        return mapper.categoryToCategoryDto(categoryRepository.save(mapper.categoryDtoToCategory(categoryDto)));    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        log.info("Fetching user {}", categoryId);
        return categoryRepository
                .findById(categoryId)
                .map(mapper::categoryToCategoryDto)
                .orElseThrow(() -> new NoSuchElementFoundException("Category not found"));
    }

    @Override
    public List<CategoryDto> getCategories() {
        log.info("Fetching all categories");
        return categoryRepository
                .findAll()
                .stream()
                .map(mapper::categoryToCategoryDto)
                .toList();
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        Category foundcCategory=categoryRepository
                .findById(id)
                .orElseThrow(()-> new NoSuchElementFoundException("Category not found"));
        categoryRepository.delete(foundcCategory);
    }
}