package com.signature.service;

import com.signature.bootstrap.Bootstrap;
import com.signature.model.Customer;
import com.signature.repository.CategoryRepository;
import com.signature.repository.CustomerRepository;
import com.signature.repository.VendorRepository;
import com.signature.service.impl.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerServiceITest {

  private CustomerService customerService;

  @Autowired
  public CategoryRepository categoryRepository;

  @Autowired
  public CustomerRepository customerRepository;

  @Autowired
  public VendorRepository vendorRepository;

  @BeforeEach
  public void setUp() throws Exception {
    log.info("Started loading initial data");

    new Bootstrap(categoryRepository, customerRepository, vendorRepository).run();

    log.info("Finished loading initial data");

    customerService = new CustomerServiceImpl(customerRepository);
  }

  @Test
  @Order(1)
  @DirtiesContext
  void addCustomer() {
    long count = customerRepository.count();

    Customer customer = new Customer("Pranjal", "Singh");

    Customer savedCustomer = customerService.addCustomer(customer);

    assertNotNull(savedCustomer, "Failed to save customer!");

    assertEquals(count + 1, savedCustomer.getId());
    assertEquals("Pranjal", savedCustomer.getFirstName());
    assertEquals("Singh", savedCustomer.getLastName());
  }

  @Test
  @Order(2)
  @DirtiesContext
  void updateCustomer() throws Exception {
    Customer customer = new Customer(2L, "Pranjal", "Singh");

    Customer updated = customerService.updateCustomer(customer);

    assertNotNull(updated, "Failed to update customer!");

    assertEquals(2L, updated.getId());
    assertEquals("Pranjal", updated.getFirstName());
    assertEquals("Singh", updated.getLastName());
  }

  @Test
  @Order(3)
  @DirtiesContext
  public void patchCustomerUpdateFirstName() throws Exception {
    final String updatedFirstName = "Pranjal";

    Customer originalCustomer = customerService.getCustomer(2L);

    assertNotNull(originalCustomer, "Failed to get customer!");

    //save original first name
    String originalFirstName = originalCustomer.getFirstName();
    String originalLastName = originalCustomer.getLastName();

    Customer customer = new Customer();
    customer.setFirstName(updatedFirstName);
    customer.setId(originalCustomer.getId());

    Customer updatedCustomer = customerService.patchCustomer(customer);

    assertNotNull(updatedCustomer, "Failed to patch customer!");

    assertEquals(updatedFirstName, updatedCustomer.getFirstName());
    assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
    assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
  }

  @Test
  @Order(4)
  @DirtiesContext
  public void patchCustomerUpdateLastName() throws Exception {
    final String updatedLastName = "Suryavanshi";

    Customer originalCustomer = customerService.getCustomer(2L);

    assertNotNull(originalCustomer, "Failed to get customer!");

    //save original last name
    String originalFirstName = originalCustomer.getFirstName();
    String originalLastName = originalCustomer.getLastName();

    Customer customer = new Customer();
    customer.setLastName(updatedLastName);
    customer.setId(originalCustomer.getId());

    Customer updatedCustomer = customerService.patchCustomer(customer);

    assertNotNull(updatedCustomer, "Failed to patch customer!");

    assertEquals(updatedLastName, updatedCustomer.getLastName());
    assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
    assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
  }

  @Test
  @Order(5)
  @DirtiesContext
  void getCustomer() throws Exception {
    Customer customer = customerService.getCustomer(1L);

    assertNotNull(customer, "Null customer returned!");
  }

  @Test
  @Order(6)
  @DirtiesContext
  void getAllCustomers() {
    List<Customer> customers = customerService.getAllCustomers();

    assertNotNull(customers, "Null customers returned!");
  }

  @Test
  @Order(7)
  @DirtiesContext
  void deleteCustomer() {
    long count = customerRepository.count();

    customerService.deleteCustomer(1L);

    assertEquals(count - 1, customerRepository.count());
  }
}