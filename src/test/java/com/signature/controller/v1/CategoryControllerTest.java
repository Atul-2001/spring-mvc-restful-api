package com.signature.controller.v1;

import com.signature.mapper.CategoryMapper;
import com.signature.model.Category;
import com.signature.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class CategoryControllerTest {

  private MockMvc mockMvc;

  @Mock
  public CategoryService categoryService;

  public CategoryController categoryController;

  @BeforeEach
  void setUp() {
    categoryController = new CategoryController(CategoryMapper.INSTANCE, categoryService);
    mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
  }

  @Test
  @Order(1)
  void getAllCategories() throws Exception {
    Category category2 = new Category(1L, "Dried");
    Category category1 = new Category(2L, "Fruits");
    List<Category> categories = Arrays.asList(category1, category2);

    when(categoryService.getAll()).thenReturn(categories);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  @Order(2)
  void getCategoryByName() throws Exception {
    Category category1 = new Category(1L, "Fruits");

    when(categoryService.getByName(anyString())).thenReturn(category1);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/Fruits")
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Fruits")))
            .andExpect(jsonPath("$.category_url", equalTo("/api/v1/categories/1")));
  }
}