package com.airheads.inventory.controller;

import com.airheads.inventory.dto.CategoryDto;
import com.airheads.inventory.service.CategoryService;
import com.airheads.inventory.service.CategoryServiceImpl;
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
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<CategoryDto> categoryDtoArgumentCaptor;

    @Test
    void testSaveOrUpdateCategory() throws Exception {
        CategoryDto ValidCategory = CategoryDto.builder()
                .categoryId(5L)
                .categoryName("Machine")
                .build();

        mockMvc
                .perform(post("/inventory/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ValidCategory))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/inventory/category")));

        verify(categoryService).saveOrUpdateCategory(any(CategoryDto.class));
        verify(categoryService).saveOrUpdateCategory(categoryDtoArgumentCaptor.capture());
        AssertionsForClassTypes.assertThat(categoryDtoArgumentCaptor.getValue().getCategoryName()).isEqualTo("Machine");
    }

    @Test
    void get_category_by_Id() throws Exception {

        CategoryDto ValidCategory = CategoryDto.builder()
                .categoryId(5L)
                .categoryName("Machine")
                .build();
        when(categoryService.getCategoryById(5L)).thenReturn(ValidCategory);

        mockMvc
                .perform(get("/inventory/category/{id}", 5L))
                .andExpect(status().isOk());
    }

    @Test
    void delete_category() throws Exception {

        mockMvc
                .perform(delete("/inventory/category/{id}", 3L).with(csrf()))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategory(any());
    }


    @Test
    void get_categories() throws Exception {

        CategoryDto category1 = CategoryDto.builder()
                .categoryId(9L)
                .categoryName("lEYLA")
                .build();

        CategoryDto category2 = CategoryDto.builder()
                .categoryId(10L)
                .categoryName("lEYLA2")
                .build();

        List<CategoryDto> categoryList = List.of(category1, category2);

        when(categoryService.getCategories()).thenReturn(categoryList);

        mockMvc
                .perform(get("/inventory/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].categoryName").value("lEYLA"));
    }

}