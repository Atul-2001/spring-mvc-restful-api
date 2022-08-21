package com.signature.service.impl;

import com.signature.exception.ResourceNotFoundException;
import com.signature.exception.ResourceUpdateFailedException;
import com.signature.model.Customer;
import com.signature.repository.CustomerRepository;
import com.signature.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
  public Customer updateCustomer(Customer customer) throws Exception {
    final Integer rowAffected = customerRepository.update(customer);
    if (rowAffected == 0) {
      throw new ResourceUpdateFailedException("Failed to update customer with id " + customer.getId());
    }
    return getCustomer(customer.getId());
  }

  @Override
  public Customer patchCustomer(Customer customer) throws Exception {
    final Customer existingCustomer = getCustomer(customer.getId());
    if (existingCustomer == null) {
      throw new ResourceNotFoundException("Customer with id " + customer.getId() + " not found");
    } else {
      if (customer.getFirstName() != null) {
        existingCustomer.setFirstName(customer.getFirstName());
      }
      if (customer.getLastName() != null) {
        existingCustomer.setLastName(customer.getLastName());
      }
      final Integer rowAffected = customerRepository.update(existingCustomer);
      if (rowAffected == 0) {
        throw new ResourceUpdateFailedException("Failed to patch customer with id " + customer.getId());
      }
      return getCustomer(customer.getId());
    }
  }

  @Override
  public Customer getCustomer(long customerId) throws Exception {
    try {
      return customerRepository.findById(customerId).orElseThrow();
    } catch (final NoSuchElementException ex) {
      throw new ResourceNotFoundException("Customer with id " + customerId + " not found");
    }
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