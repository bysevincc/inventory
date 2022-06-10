package com.airheads.inventory.mapper;


import com.airheads.inventory.dto.CategoryDto;
import com.airheads.inventory.dto.ProductDto;
import com.airheads.inventory.entity.Category;
import com.airheads.inventory.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {

    CategoryDto categoryToCategoryDto(Category category);
    ProductDto productToProductDto(Product product);
    Product productDtoToProduct(ProductDto productDto);
    Category categoryDtoToCategory(CategoryDto categoryDto);
}
