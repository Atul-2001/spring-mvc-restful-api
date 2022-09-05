package com.signature.controller.v1;

import com.signature.domain.CustomerDTO;
import com.signature.mapper.CustomerMapper;
import com.signature.model.Customer;
import com.signature.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customer Controller", description = "Customer API")
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
  @Operation(summary = "Create a new customer")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Customer created",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CustomerDTO.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
          @ApiResponse(responseCode = "409", description = "Customer already exists", content = @Content)
  })
  public CustomerDTO createCustomer(@RequestBody final CustomerDTO customerDTO) {
    final Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    return customerToCustomerDto(customerService.addCustomer(customer));
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get a customer by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Customer found",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CustomerDTO.class))),
          @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
  })
  public CustomerDTO getCustomer(@PathVariable final Long id) throws Exception {
    return customerMapper.customerToCustomerDto(customerService.getCustomer(id));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get all customers")
  @ApiResponse(responseCode = "200", description = "Found all customers",
          content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CustomerDTO.class))))
  public List<CustomerDTO> getAllCustomers() {
    return customerService.getAllCustomers().stream()
            .map(this::customerToCustomerDto).collect(Collectors.toList());
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update an existing customer")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Customer updated",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CustomerDTO.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
          @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
  })
  public CustomerDTO updateCustomer(@PathVariable final Long id,
                                    @RequestBody final CustomerDTO customerDTO) throws Exception {
    Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    customer.setId(id);
    return customerMapper.customerToCustomerDto(customerService.updateCustomer(customer));
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Patch an existing customer")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Customer patched",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CustomerDTO.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
          @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
  })
  public CustomerDTO patchCustomer(@PathVariable final Long id,
                                   @RequestBody final CustomerDTO customerDTO) throws Exception {
    Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
    customer.setId(id);
    return customerMapper.customerToCustomerDto(customerService.patchCustomer(customer));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Delete a customer")
  @ApiResponse(responseCode = "200", description = "Customer deleted")
  public void deleteCustomer(@PathVariable final Long id) {
    customerService.deleteCustomer(id);
  }
}