package com.signature.controller.v1;

import com.signature.domain.CategoryDTO;
import com.signature.mapper.CategoryMapper;
import com.signature.model.Category;
import com.signature.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private final CategoryMapper categoryMapper;
  private final CategoryService categoryService;

  public CategoryController(CategoryMapper categoryMapper,
                            CategoryService categoryService) {
    this.categoryMapper = categoryMapper;
    this.categoryService = categoryService;
  }

  private CategoryDTO categoryToCategoryDto(final Category category) {
    CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
    categoryDTO.setCategoryUrl("/api/v1/categories/" + category.getId());
    return categoryDTO;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryDTO> getAllCategories() {
    return categoryService.getAll().stream().map(this::categoryToCategoryDto).collect(Collectors.toList());
  }


  private CategoryDTO getCategoryById(final Long id) throws Exception {
    return categoryToCategoryDto(categoryService.getById(id));
  }

  public CategoryDTO getCategoryByName(String name) throws Exception {
    return categoryToCategoryDto(categoryService.getByName(name));
  }

  @GetMapping("/{identifier}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryDTO getCategoryByIdentifier(@PathVariable String identifier) throws Exception {
    if (NumberUtils.isParsable(identifier))
      return getCategoryById(Long.parseLong(identifier));
    else
      return getCategoryByName(identifier);
  }
}