package com.signature.service;

import com.signature.model.Customer;
import com.signature.repository.CustomerRepository;
import com.signature.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  private CustomerService customerService;

  @Mock
  public CustomerRepository customerRepository;

  @BeforeEach
  void setUp() {
    customerService = new CustomerServiceImpl(customerRepository);
  }

  @Test
  void addCustomer() {
    //given
    Customer customer = new Customer(1L, "Atul", "Singh");

    //when
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    //then
    Customer savedCustomer = customerService.addCustomer(customer);

    assertNotNull(savedCustomer, "Failed to save customer!");

    verify(customerRepository, times(1)).save(any(Customer.class));

    assertEquals(1L, savedCustomer.getId());
    assertEquals("Atul", savedCustomer.getFirstName());
    assertEquals("Singh", savedCustomer.getLastName());
  }

  @Test
  void updateCustomer() {
    //given
    Customer customer = new Customer(1L, "Atul", "Singh");

    customerRepository.save(customer);

    Customer updated = new Customer(1L, "Rishu", "Singh");

    //when
    when(customerRepository.update(any(Customer.class))).thenReturn(1);
    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(updated));

    //then
    Customer updatedCustomer = customerService.updateCustomer(updated);

    assertNotNull(updatedCustomer, "Failed to update customer!");

    verify(customerRepository, times(1)).update(any(Customer.class));

    assertEquals(1L, updatedCustomer.getId());
    assertEquals("Rishu", updatedCustomer.getFirstName());
    assertEquals("Singh", updatedCustomer.getLastName());
  }

  @Test
  void getCustomer() {
    //given
    Customer customer = new Customer(1L, "Atul", "Singh");

    //when
    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

    //then
    Customer customer1 = customerService.getCustomer(1L);
    assertNotNull(customer1, "Null customer returned!");
    verify(customerRepository, times(1)).findById(anyLong());
  }

  @Test
  void getAllCustomers() {
    //given
    List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

    //when
    when(customerRepository.findAll()).thenReturn(customers);

    //then
    List<Customer> customerList = customerService.getAllCustomers();
    assertEquals(customers.size(), customerList.size());
    verify(customerRepository, times(1)).findAll();
  }

  @Test
  void deleteCustomer() {
    customerService.deleteCustomer(1L);

    verify(customerRepository, times(1)).deleteById(anyLong());
  }
}