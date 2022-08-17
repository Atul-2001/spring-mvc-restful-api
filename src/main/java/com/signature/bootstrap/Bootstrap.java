package com.signature.bootstrap;

import com.signature.model.Category;
import com.signature.model.Customer;
import com.signature.repository.CategoryRepository;
import com.signature.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final CustomerRepository customerRepository;

  public Bootstrap(final CategoryRepository categoryRepository,
                   final CustomerRepository customerRepository) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
  }

  private void loadCategories() throws Exception {
    categoryRepository.save(new Category("Fruits"));
    categoryRepository.save(new Category("Dried"));
    categoryRepository.save(new Category("Fresh"));
    categoryRepository.save(new Category("Exotic"));
    categoryRepository.save(new Category("Nuts"));

    log.info("No. of categories added : {}", categoryRepository.count());
  }

  private void loadCustomers() throws Exception {
    customerRepository.save(new Customer("Rishu", "Singh"));
    customerRepository.save(new Customer("Atul", "Singh"));
    customerRepository.save(new Customer("Chotu", "Singh"));
    customerRepository.save(new Customer("Abhishek", "Singh"));
    customerRepository.save(new Customer("Shivang", "Verma"));
    customerRepository.save(new Customer("Vivek", "Pandey"));
    customerRepository.save(new Customer("Saumil", "Thripathi"));

    log.info("No. of customers added : {}", customerRepository.count());
  }

  @Override
  public void run(String... args) throws Exception {
    loadCategories();
    loadCustomers();
  }
}