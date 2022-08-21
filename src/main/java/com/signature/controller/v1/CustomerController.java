package com.signature.controller.v1;

import com.signature.domain.CustomerDTO;
import com.signature.mapper.CustomerMapper;
import com.signature.model.Customer;
import com.signature.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

  private CustomerDTO customerToCustomerDto(final Customer customer) {
    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
    return customerDTO;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDTO createCustomer(@RequestBody final CustomerDTO customerDTO) {
    final Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    return customerToCustomerDto(customerService.addCustomer(customer));
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO getCustomer(@PathVariable final Long id) throws Exception {
    return customerMapper.customerToCustomerDto(customerService.getCustomer(id));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CustomerDTO> getAllCustomers() {
    return customerService.getAllCustomers().stream()
            .map(this::customerToCustomerDto).collect(Collectors.toList());
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO updateCustomer(@PathVariable final Long id,
                                    @RequestBody final CustomerDTO customerDTO) throws Exception {
    Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    customer.setId(id);
    return customerMapper.customerToCustomerDto(customerService.updateCustomer(customer));
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO patchCustomer(@PathVariable final Long id,
                                   @RequestBody final CustomerDTO customerDTO) throws Exception {
    Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    customer.setId(id);
    return customerMapper.customerToCustomerDto(customerService.patchCustomer(customer));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCustomer(@PathVariable final Long id) {
    customerService.deleteCustomer(id);
  }
}