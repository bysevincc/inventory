package com.airheads.inventory.controller;

import com.airheads.inventory.dto.CategoryDto;
import com.airheads.inventory.dto.OnCreate;
import com.airheads.inventory.dto.OnUpdate;
import com.airheads.inventory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class CategoryController {

    private final CategoryService categoryService;

    @Validated(OnCreate.class)
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand()
                .toUri();
        return ResponseEntity
                .created(location)
                .body(categoryService.saveOrUpdateCategory(categoryDto));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> listOfCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId) {
        return  ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    @DeleteMapping(value = "/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") Long categoryId) {

      categoryService.deleteCategory(categoryId);
    }

    @Validated(OnUpdate.class)
    @PutMapping("/category")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto)
    {
        return ResponseEntity.ok(categoryService.saveOrUpdateCategory(categoryDto));
    }
}
