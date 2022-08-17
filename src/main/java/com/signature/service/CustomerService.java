package com.signature.service;

import com.signature.model.Customer;

import java.util.List;

public interface CustomerService {

  Customer addCustomer(Customer customer);

  Customer updateCustomer(Customer customer);

  Customer getCustomer(long customerId);

  List<Customer> getAllCustomers();

  void deleteCustomer(long customerId);
}
