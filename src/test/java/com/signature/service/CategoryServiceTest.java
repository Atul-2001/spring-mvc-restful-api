package com.signature.service;

import com.signature.domain.CategoryDTO;
import com.signature.mapper.CategoryMapper;
import com.signature.model.Category;
import com.signature.repository.CategoryRepository;
import com.signature.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

  private CategoryService categoryService;

  @Mock
  public CategoryRepository categoryRepository;

  @BeforeEach
  void setUp() throws Exception {
    try (AutoCloseable openMocks = MockitoAnnotations.openMocks(this)) {
      categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }
  }

  @Test
  void getByName() {
    //given
    Category category = new Category();
    category.setId(1L);
    category.setName("Fruits");

    when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

    //when
    CategoryDTO categoryDTO = categoryService.getByName("Fruits");

    //then
    assertEquals(1L, categoryDTO.getId());
    assertEquals("Fruits", categoryDTO.getName());
  }

  @Test
  void getById() {
    //given
    Category category = new Category();
    category.setId(1L);
    category.setName("Fruits");

    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

    //when
    CategoryDTO categoryDTO = categoryService.getById(1L);

    //then
    assertEquals(1L, categoryDTO.getId());
    assertEquals("Fruits", categoryDTO.getName());
  }

  @Test
  void getAll() {
    //given
    List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

    when(categoryRepository.findAll()).thenReturn(categories);

    //when
    List<CategoryDTO> categoryDTOs = categoryService.getAll();

    //then
    assertEquals(3, categoryDTOs.size());
  }
}