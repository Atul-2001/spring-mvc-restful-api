package com.signature.service;

import com.signature.model.Category;

import java.util.List;

public interface CategoryService {

  Category getByName(String name);

  Category getById(Long id);

  List<Category> getAll();
}