package com.airheads.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long categoryId;

    @NotBlank
    private String categoryName;
}
