package com.signature.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signature.mapper.CustomerMapper;
import com.signature.model.Customer;
import com.signature.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @Mock
  public CustomerService customerService;

  public CustomerController customerController;

  @BeforeEach
  void setUp() {
    customerController = new CustomerController(CustomerMapper.INSTANCE, customerService);
    mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void createCustomer() throws Exception {
    //given
    Customer customer = new Customer(1L, "Atul", "Singh");

    //when
    when(customerService.addCustomer(any(Customer.class))).thenReturn(customer);

    //then
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                    .content(objectMapper.writeValueAsString(new Customer("Atul", "Singh")))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", equalTo("Atul")))
            .andExpect(jsonPath("$.lastName", equalTo("Singh")));
  }

  @Test
  void getCustomer() throws Exception {
    //given
    Customer customer = new Customer(1L, "Atul", "Singh");

    //when
    when(customerService.getCustomer(anyLong())).thenReturn(customer);

    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", equalTo("Atul")))
            .andExpect(jsonPath("$.lastName", equalTo("Singh")));
  }

  @Test
  void getAllCustomers() throws Exception {
    //given
    Customer customer1 = new Customer(1L, "Rishu", "Singh");
    Customer customer2 = new Customer(2L, "Atul", "Singh");
    Customer customer3 = new Customer(3L, "Chotu", "Singh");
    List<Customer> customers = Arrays.asList(customer1, customer2, customer3);

    //when
    when(customerService.getAllCustomers()).thenReturn(customers);

    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void updateCustomer() throws Exception {
    //given
    Customer oldCustomer = new Customer(1L, "Rishu", "Singh");

    customerService.addCustomer(oldCustomer);

    Customer customer = new Customer(1L, "Atul", "Singh");

    //when
    when(customerService.getCustomer(anyLong())).thenReturn(oldCustomer);
    when(customerService.updateCustomer(any(Customer.class))).thenReturn(customer);

    //then
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/1")
                    .content(objectMapper.writeValueAsString(new Customer("Atul", "Singh")))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", equalTo("Atul")))
            .andExpect(jsonPath("$.lastName", equalTo("Singh")));
  }

  @Test
  void deleteCustomer() throws Exception {
    //when
    when(customerService.getCustomer(1L)).thenReturn(new Customer(1L, "Rishu", "Singh"));

    //then
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/1")).andExpect(status().isOk());
  }
}