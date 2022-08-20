package com.signature.service;

import com.signature.model.Category;

import java.util.List;

public interface CategoryService {

  Category getByName(String name) throws Exception;

  Category getById(Long id) throws Exception;

  List<Category> getAll();
}