package com.signature.controller.v1;

import com.signature.domain.CategoryDTO;
import com.signature.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

class CategoryControllerTest {

  private MockMvc mockMvc;

  @Mock
  public CategoryService categoryService;

  @InjectMocks
  public CategoryController categoryController;

  @BeforeEach
  void setUp() throws Exception {
    try (AutoCloseable openMocks = MockitoAnnotations.openMocks(this)) {
      mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }
  }

  @Test
  void getAllCategories() throws Exception {
    CategoryDTO category2 = new CategoryDTO(1L, "Dried");
    CategoryDTO category1 = new CategoryDTO(2L, "Fruits");
    List<CategoryDTO> categories = Arrays.asList(category1, category2);

    when(categoryService.getAll()).thenReturn(categories);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void getCategoryByName() throws Exception {
    CategoryDTO category1 = new CategoryDTO(1L, "Fruits");

    when(categoryService.getByName(anyString())).thenReturn(category1);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/Fruits")
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Fruits")));
  }
}