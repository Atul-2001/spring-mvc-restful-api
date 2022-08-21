package com.signature.bootstrap;

import com.signature.model.Category;
import com.signature.model.Customer;
import com.signature.model.Vendor;
import com.signature.repository.CategoryRepository;
import com.signature.repository.CustomerRepository;
import com.signature.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final CustomerRepository customerRepository;
  private final VendorRepository vendorRepository;

  public Bootstrap(final CategoryRepository categoryRepository,
                   final CustomerRepository customerRepository,
                   final VendorRepository vendorRepository) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
    this.vendorRepository = vendorRepository;
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

  private void loadVendors() throws Exception {
    vendorRepository.save(new Vendor("Signature Technologies Ltd."));
    vendorRepository.save(new Vendor("Vandela Technologies Ltd."));
    vendorRepository.save(new Vendor("Apple Technologies Ltd."));
    vendorRepository.save(new Vendor("Microsoft Technologies Ltd."));
    vendorRepository.save(new Vendor("Google Technologies Ltd."));
    vendorRepository.save(new Vendor("Facebook Technologies Ltd."));

    log.info("No. of vendors added : {}", vendorRepository.count());
  }

  @Override
  public void run(String... args) throws Exception {
    loadCategories();
    loadCustomers();
    loadVendors();
  }
}