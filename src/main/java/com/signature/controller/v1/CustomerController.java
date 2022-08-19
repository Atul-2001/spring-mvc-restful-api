package com.signature.controller.v1;

import com.signature.domain.CustomerDTO;
import com.signature.mapper.CustomerMapper;
import com.signature.model.Customer;
import com.signature.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private final CustomerMapper customerMapper;
  private final CustomerService customerService;

  public CustomerController(final CustomerMapper customerMapper,
                            final CustomerService customerService) {
    this.customerMapper = customerMapper;
    this.customerService = customerService;
  }

  @PostMapping
  public ResponseEntity<?> createCustomer(@RequestBody final CustomerDTO customerDTO) {
    final Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    final Customer newCustomer = customerService.addCustomer(customer);
    final CustomerDTO newCustomerDTO = customerMapper.customerToCustomerDto(newCustomer);
    final URI location = URI.create("/api/v1/customers/" + customer.getId());
    return ResponseEntity.status(HttpStatus.CREATED).location(location).body(newCustomerDTO);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getCustomer(@PathVariable final Long id) {
    final Customer customer = customerService.getCustomer(id);
    return ResponseEntity.ok(customerMapper.customerToCustomerDto(customer));
  }

  private CustomerDTO customerToCustomerDto(final Customer customer) {
    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
    return customerDTO;
  }

  @GetMapping
  public ResponseEntity<?> getAllCustomers() {
    return ResponseEntity.ok(customerService.getAllCustomers().stream()
            .map(this::customerToCustomerDto).collect(Collectors.toList()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCustomer(@PathVariable final Long id,
                                          @RequestBody final CustomerDTO customerDTO) {
    Customer customer = customerService.getCustomer(id);
    if (customer == null) {
      return ResponseEntity.notFound().build();
    } else {
      customer = customerMapper.customerDtoToCustomer(customerDTO);
      customer.setId(id);
      final Customer updatedCustomer = customerService.updateCustomer(customer);
      return ResponseEntity.ok(customerMapper.customerToCustomerDto(updatedCustomer));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable final Long id) {
    final Customer customer = customerService.getCustomer(id);
    if (customer == null) {
      return ResponseEntity.notFound().build();
    } else {
      customerService.deleteCustomer(id);
      return ResponseEntity.ok().build();
    }
  }
}