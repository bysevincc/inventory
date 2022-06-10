package com.airheads.inventory.controller;

import com.airheads.inventory.dto.ProductDto;
import com.airheads.inventory.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<ProductDto> productDtoArgumentCaptor;

    @Test
    void createProduct() throws Exception {
        ProductDto ValidProduct = ProductDto.builder()
                .productId(5L)
                .productName("Laptop")
                .productType("Machine")
                .productSellingPrice(100L)
                .description("19 RAM")
                .vendor("HP")
                .weight("2")
                .weightUnit("KG")
                .barcode("TEST")
                .tag("it")
                .build();

        mockMvc
                .perform(post("/inventory/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ValidProduct))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/inventory/product")));

        verify(productService).saveProduct(any(ProductDto.class));
        verify(productService).saveProduct(productDtoArgumentCaptor.capture());
        AssertionsForClassTypes.assertThat(productDtoArgumentCaptor.getValue().getProductName()).isEqualTo("Laptop");
    }

    @Test
    void get_product_by_Id() throws Exception {

        ProductDto ValidProduct = ProductDto.builder()
                .productId(5L)
                .productName("Laptop")
                .productType("Machine")
                .productSellingPrice(100L)
                .description("16 RAM")
                .vendor("HP")
                .weight("2")
                .weightUnit("KG")
                .barcode("TEST")
                .tag("it")
                .build();
        when(productService.getProductById(5L)).thenReturn(ValidProduct);

        mockMvc
                .perform(get("/inventory/product/{id}", 5L))
                .andExpect(status().isOk());
    }


    @Test
    void delete_product() throws Exception {

        mockMvc
                .perform(delete("/inventory/product/{id}", 3L).with(csrf()))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(any());

    }

    @Test
    void get_products() throws Exception {
        ProductDto product1 = ProductDto.builder()
                .productId(5L)
                .productName("Ketchup")
                .productType("Food")
                .productSellingPrice(5L)
                .description("sweet")
                .vendor("Calve")
                .weight("700")
                .weightUnit("gr")
                .barcode("TEST")
                .tag("fast food")
                .build();

        ProductDto product2 = ProductDto.builder()
                .productId(5L)
                .productName("mayonnaise")
                .productType("Food")
                .productSellingPrice(5L)
                .description("vegan")
                .vendor("Tat")
                .weight("700")
                .weightUnit("gr")
                .barcode("TEST")
                .tag("fast food")
                .build();

        List<ProductDto> productList = List.of(product1, product2);

        when(productService.getProducts()).thenReturn(productList);

        mockMvc
                .perform(get("/inventory/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].productName").value("Ketchup"))
                .andExpect(jsonPath("$[0].vendor").value("Calve"));
    }


}