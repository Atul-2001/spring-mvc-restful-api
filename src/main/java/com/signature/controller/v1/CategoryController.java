package com.signature.controller.v1;

import com.signature.domain.CategoryDTO;
import com.signature.mapper.CategoryMapper;
import com.signature.model.Category;
import com.signature.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category Controller", description = "Category API")
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
  @Operation(summary = "Get all categories")
  @ApiResponse(responseCode = "200", description = "Found all categories",
          content = {@Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class)))})
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
  @Operation(summary = "Get category by id or name")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Found category",
                  content = {@Content(mediaType = "application/json",
                          schema = @Schema(implementation = CategoryDTO.class))}),
          @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Category not found",
                  content = @Content)})
  public CategoryDTO getCategoryByIdentifier(@PathVariable String identifier) throws Exception {
    if (NumberUtils.isParsable(identifier))
      return getCategoryById(Long.parseLong(identifier));
    else
      return getCategoryByName(identifier);
  }
}