package com.signature.service;

import com.signature.model.Category;
import com.signature.repository.CategoryRepository;
import com.signature.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class CategoryServiceTest {

  private CategoryService categoryService;

  @Mock
  public CategoryRepository categoryRepository;

  @BeforeEach
  void setUp() {
    categoryService = new CategoryServiceImpl(categoryRepository);
  }

  @Test
  @Order(1)
  void getByName() throws Exception {
    //given
    Category category = new Category(1L, "Fruits");

    when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

    //when
    Category category1 = categoryService.getByName("Fruits");

    //then
    assertEquals(1L, category1.getId());
    assertEquals("Fruits", category1.getName());
  }

  @Test
  @Order(2)
  void getById() throws Exception {
    //given
    Category category = new Category(1L, "Fruits");

    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

    //when
    Category category1 = categoryService.getById(1L);

    //then
    assertEquals(1L, category1.getId());
    assertEquals("Fruits", category1.getName());
  }

  @Test
  @Order(3)
  void getAll() {
    //given
    List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

    when(categoryRepository.findAll()).thenReturn(categories);

    //when
    List<Category> categoryList = categoryService.getAll();

    //then
    assertEquals(3, categoryList.size());
  }
}