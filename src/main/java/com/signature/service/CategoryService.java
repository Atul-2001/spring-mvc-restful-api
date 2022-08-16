package com.signature.service;

import com.signature.domain.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO getByName(String name);

    CategoryDTO getById(Long id);

    List<CategoryDTO> getAll();
}