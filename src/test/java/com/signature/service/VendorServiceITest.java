package com.signature.service;

import com.signature.bootstrap.Bootstrap;
import com.signature.model.Vendor;
import com.signature.repository.CategoryRepository;
import com.signature.repository.CustomerRepository;
import com.signature.repository.VendorRepository;
import com.signature.service.impl.VendorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class VendorServiceITest {

  private VendorService vendorService;

  @Autowired
  public VendorRepository vendorRepository;

  @Autowired
  public CustomerRepository customerRepository;

  @Autowired
  public CategoryRepository categoryRepository;

  @BeforeEach
  void setUp() throws Exception {
    log.info("Started loading initial data");

    new Bootstrap(categoryRepository, customerRepository, vendorRepository).run();

    log.info("Finished loading initial data");

    vendorService = new VendorServiceImpl(vendorRepository);
  }

  @Test
  @Order(1)
  @DirtiesContext
  void addVendor() {
    long count = vendorRepository.count();

    Vendor vendor = new Vendor("Pranjal and Atul Singh Foundation");

    Vendor savedVendor = vendorService.addVendor(vendor);

    assertNotNull(vendor, "Failed to save vendor!");

    assertEquals(count + 1, savedVendor.getId());
    assertEquals("Pranjal and Atul Singh Foundation", savedVendor.getName());
  }

  @Test
  @Order(2)
  @DirtiesContext
  void updateVendor() throws Exception {
    Vendor existingVendor = vendorService.getVendor(1L);

    Vendor vendor = new Vendor(1L, "Signature Technologies");

    Vendor updated = vendorService.updateVendor(vendor);

    assertNotNull(updated, "Failed to update vendor!");

    assertEquals("Signature Technologies", updated.getName());
    assertThat(updated.getName(), not(equalTo(existingVendor.getName())));
  }

  @Test
  @Order(3)
  @DirtiesContext
  void patchVendor() throws Exception {
    final Vendor existingVendor = vendorService.getVendor(2L);
    final String existingName = existingVendor.getName();

    Vendor vendor = new Vendor(2L, "Vanilla Technologies");

    Vendor patched = vendorService.patchVendor(vendor);

    assertNotNull(patched, "Failed to update vendor!");

    assertThat(patched.getName(), not(equalTo(existingName)));
    assertEquals("Vanilla Technologies", patched.getName());
  }

  @Test
  @Order(4)
  @DirtiesContext
  void getVendor() throws Exception {
    Vendor vendor = vendorService.getVendor(1L);

    assertNotNull(vendor, "Failed to get vendor!");
  }

  @Test
  @Order(5)
  @DirtiesContext
  void getAllVendors() {
    List<Vendor> vendors = vendorService.getAllVendors();

    assertNotNull(vendors, "Failed to get vendors!");

    assertEquals(6, vendors.size());
  }

  @Test
  @Order(6)
  @DirtiesContext
  void deleteVendor() {
    long count = vendorRepository.count();

    vendorService.deleteVendor(1L);

    assertEquals(count - 1, vendorRepository.count());
  }
}