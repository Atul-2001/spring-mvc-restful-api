package com.signature.service.impl;

import com.signature.domain.CategoryDTO;
import com.signature.mapper.CategoryMapper;
import com.signature.repository.CategoryRepository;
import com.signature.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryMapper categoryMapper;
  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(final CategoryMapper categoryMapper,
                             final CategoryRepository categoryRepository) {
    this.categoryMapper = categoryMapper;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public CategoryDTO getByName(String name) {
    return categoryRepository.findByName(name)
            .map(categoryMapper::categoryToCategoryDTO)
            .orElse(null);
  }

  @Override
  public CategoryDTO getById(Long id) {
    return categoryRepository.findById(id)
            .map(categoryMapper::categoryToCategoryDTO)
            .orElse(null);
  }

  @Override
  public List<CategoryDTO> getAll() {
    return categoryRepository.findAll().stream()
            .map(categoryMapper::categoryToCategoryDTO)
            .collect(Collectors.toList());
  }
}