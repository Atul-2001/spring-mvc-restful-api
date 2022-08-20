package com.signature.service;

import com.signature.exception.ResourceNotFoundException;
import com.signature.model.Customer;

import java.util.List;

public interface CustomerService {

  Customer addCustomer(Customer customer);

  Customer updateCustomer(Customer customer) throws Exception;

  Customer patchCustomer(Customer customer) throws Exception;

  Customer getCustomer(long customerId) throws Exception;

  List<Customer> getAllCustomers();

  void deleteCustomer(long customerId);
}
