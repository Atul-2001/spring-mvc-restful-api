package com.signature.mapper;

import com.signature.domain.CategoryDTO;
import com.signature.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

  private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

  @Test
  void categoryToCategoryDTO() {
    //given
    Category category = new Category();
    category.setName("Fruits");
    category.setId(1L);

    //when
    CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

    //then
    assertEquals(1L, categoryDTO.getId());
    assertEquals("Fruits", categoryDTO.getName());
  }

  @Test
  void categoryDtoToCategory() {
    //given
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setName("Fruits");
    categoryDTO.setId(1L);

    //when
    Category category = categoryMapper.categoryDtoToCategory(categoryDTO);

    //then
    assertEquals(1L, category.getId());
    assertEquals("Fruits", category.getName());
  }
}