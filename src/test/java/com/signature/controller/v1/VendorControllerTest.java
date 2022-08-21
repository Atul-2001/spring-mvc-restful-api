package com.signature.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signature.domain.VendorDTO;
import com.signature.mapper.VendorMapper;
import com.signature.model.Vendor;
import com.signature.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
@TestMethodOrder(OrderAnnotation.class)
class VendorControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @Mock
  public VendorService vendorService;

  public VendorController vendorController;

  @BeforeEach
  void setUp() {
    vendorController = new VendorController(VendorMapper.INSTANCE, vendorService);
    mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  @Order(1)
  void createVendor() throws Exception {
    //given
    Vendor vendor = new Vendor(1L, "Signature Technologies");

    //when
    when(vendorService.addVendor(any(Vendor.class))).thenReturn(vendor);

    //then
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vendors")
                    .content(objectMapper.writeValueAsString(new VendorDTO("Signature Technologies")))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", equalTo("Signature Technologies")))
            .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
  }

  @Test
  @Order(2)
  void getVendor() throws Exception {
    //given
    Vendor vendor = new Vendor(1L, "Signature Technologies");

    //when
    when(vendorService.getVendor(anyLong())).thenReturn(vendor);

    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors/1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Signature Technologies")))
            .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
  }

  @Test
  @Order(3)
  void getAllVendors() throws Exception {
    //given
    Vendor vendor1 = new Vendor(1L, "Signature Technologies");
    Vendor vendor2 = new Vendor(2L, "Microsoft Technologies");
    Vendor vendor3 = new Vendor(3L, "Amazon Technologies");
    List<Vendor> vendors = Arrays.asList(vendor1, vendor2, vendor3);

    //when
    when(vendorService.getAllVendors()).thenReturn(vendors);

    //then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendors")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  @Order(4)
  void updateVendor() throws Exception {
    //given
    Vendor vendor = new Vendor(1L, "Microsoft Technologies");

    //when
    when(vendorService.updateVendor(any(Vendor.class))).thenReturn(vendor);

    //then
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vendors/1")
                    .content(objectMapper.writeValueAsString(new VendorDTO("Microsoft Technologies")))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Microsoft Technologies")))
            .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
  }

  @Test
  @Order(5)
  void patchVendor() throws Exception {
    //given
    Vendor vendor = new Vendor(1L, "Alphabet Technologies");

    //when
    when(vendorService.patchVendor(any(Vendor.class))).thenReturn(vendor);

    //then
    mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/vendors/1")
                    .content(objectMapper.writeValueAsString(new VendorDTO("Alphabet Technologies")))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Alphabet Technologies")))
            .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
  }

  @Test
  @Order(6)
  void deleteVendor() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vendors/1")).andExpect(status().isOk());
  }
}