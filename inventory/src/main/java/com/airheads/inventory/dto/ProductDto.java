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
public class ProductDto {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long productId;

    @NotBlank
    private String vendor;

    @NotBlank
    private String productName;

    @NotBlank
    private String productType;

    private String description;

    private String barcode;

    private String tag;
    private String weight;
    private String weightUnit;
    private double productSellingPrice;
}
