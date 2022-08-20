package com.signature.service.impl;

import com.signature.model.Customer;
import com.signature.repository.CustomerRepository;
import com.signature.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer addCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(Customer customer) {
    Integer rowAffected = customerRepository.update(customer);
    if (rowAffected == 0) {
      log.error("Failed to update customer with id {}", customer.getId());
      return null;
    }
    return getCustomer(customer.getId());
  }

  @Override
  public Customer patchCustomer(Customer customer) {
    Customer existingCustomer = getCustomer(customer.getId());
    if (existingCustomer == null) {
      log.error("Customer with id {} not found", customer.getId());
      return null;
    } else {
      if (customer.getFirstName() != null) {
        existingCustomer.setFirstName(customer.getFirstName());
      }
      if (customer.getLastName() != null) {
        existingCustomer.setLastName(customer.getLastName());
      }
      Integer rowAffected = customerRepository.update(existingCustomer);
      if (rowAffected == 0) {
        log.error("Failed to patch customer with id {}", customer.getId());
        return null;
      }
      return getCustomer(customer.getId());
    }
  }

  @Override
  public Customer getCustomer(long customerId) {
    return customerRepository.findById(customerId).orElse(null);
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public void deleteCustomer(long customerId) {
    customerRepository.deleteById(customerId);
  }
}