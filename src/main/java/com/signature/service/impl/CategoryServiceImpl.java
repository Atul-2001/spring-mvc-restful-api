package com.signature.service.impl;

import com.signature.exception.ResourceNotFoundException;
import com.signature.model.Category;
import com.signature.repository.CategoryRepository;
import com.signature.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(final CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category getByName(String name) throws Exception {
    try {
      return categoryRepository.findByName(name).orElseThrow();
    } catch (final NoSuchElementException ex) {
      throw new ResourceNotFoundException("Category with name " + name + " not found");
    }
  }

  @Override
  public Category getById(Long id) throws Exception {
    try {
      return categoryRepository.findById(id).orElse(null);
    } catch (final NoSuchElementException ex) {
      throw new ResourceNotFoundException("Category with id " + id + " not found");
    }
  }

  @Override
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }
}