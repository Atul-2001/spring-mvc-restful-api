package com.signature.bootstrap;

import com.signature.model.Category;
import com.signature.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

  private final CategoryRepository categoryRepository;

  public Bootstrap(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  private void loadCategories() throws Exception {
    categoryRepository.save(new Category("Fruits"));
    categoryRepository.save(new Category("Dried"));
    categoryRepository.save(new Category("Fresh"));
    categoryRepository.save(new Category("Exotic"));
    categoryRepository.save(new Category("Nuts"));

    log.info("No. of categories added : {}", categoryRepository.count());
  }

  @Override
  public void run(String... args) throws Exception {
    loadCategories();
  }
}