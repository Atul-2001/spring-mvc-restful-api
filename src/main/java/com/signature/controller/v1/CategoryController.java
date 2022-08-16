package com.signature.controller.v1;

import com.signature.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<?> getAllCategories() {
    return ResponseEntity.ok(categoryService.getAll());
  }

  @GetMapping("/{name}")
  public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
    return ResponseEntity.ok(categoryService.getByName(name));
  }
}