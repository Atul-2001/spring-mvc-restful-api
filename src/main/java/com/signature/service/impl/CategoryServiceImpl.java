package com.signature.service.impl;

import com.signature.model.Category;
import com.signature.repository.CategoryRepository;
import com.signature.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(final CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category getByName(String name) {
    return categoryRepository.findByName(name).orElse(null);
  }

  @Override
  public Category getById(Long id) {
    return categoryRepository.findById(id).orElse(null);
  }

  @Override
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }
}