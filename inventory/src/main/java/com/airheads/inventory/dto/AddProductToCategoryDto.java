package com.airheads.inventory.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddProductToCategoryDto {

    @NotNull
    private Long categoryId;

    @NotNull
    private String productName;
}
