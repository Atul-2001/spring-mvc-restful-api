package com.signature.service;

import com.signature.model.Vendor;
import com.signature.repository.VendorRepository;
import com.signature.service.impl.VendorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class VendorServiceTest {

  private VendorService vendorService;

  @Mock
  public VendorRepository vendorRepository;

  @BeforeEach
  void setUp() {
    vendorService = new VendorServiceImpl(vendorRepository);
  }

  @Test
  @Order(1)
  void addVendor() {
    //given
    Vendor vendor = new Vendor(1L, "Signature Technologies");

    //when
    when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

    //then
    Vendor savedVendor = vendorService.addVendor(vendor);

    assertNotNull(savedVendor, "Failed to save vendor!");

    assertEquals(1L, savedVendor.getId());
    assertEquals("Signature Technologies", savedVendor.getName());

    verify(vendorRepository, times(1)).save(any(Vendor.class));
  }

  @Test
  @Order(2)
  void updateVendor() throws Exception {
    //given
    Vendor vendor = new Vendor(1L, "Signature Technologies Ltd.");

    //when
    when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
    when(vendorRepository.update(any(Vendor.class))).thenReturn(1);

    //then
    Vendor savedVendor = vendorService.updateVendor(vendor);

    assertNotNull(savedVendor, "Failed to update vendor!");

    assertEquals(1L, savedVendor.getId());
    assertEquals("Signature Technologies Ltd.", savedVendor.getName());

    verify(vendorRepository, times(1)).update(any(Vendor.class));
  }

  @Test
  @Order(3)
  void patchVendor() throws Exception {
    //given
    Vendor vendor = new Vendor(1L, "Signature Technologies");

    //when
    when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
    when(vendorRepository.update(any(Vendor.class))).thenReturn(1);

    //then
    Vendor savedVendor = vendorService.patchVendor(new Vendor(1L, "Signature Technologies Ltd."));

    assertNotNull(savedVendor, "Failed to patch vendor!");

    assertEquals(1L, savedVendor.getId());
    assertEquals("Signature Technologies Ltd.", savedVendor.getName());

    verify(vendorRepository, times(1)).update(any(Vendor.class));
  }

  @Test
  @Order(4)
  void getVendor() throws Exception {
    //given
    Vendor vendor = new Vendor(1L, "Signature Technologies");

    //when
    when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));

    //then
    Vendor vendor1 = vendorService.getVendor(1L);

    assertEquals(1L, vendor1.getId());
    assertEquals("Signature Technologies", vendor1.getName());

    verify(vendorRepository, times(1)).findById(1L);
  }

  @Test
  @Order(5)
  void getAllVendors() {
    //given
    List<Vendor> vendors = List.of(new Vendor(), new Vendor(), new Vendor());

    //when
    when(vendorRepository.findAll()).thenReturn(vendors);

    //then
    List<Vendor> allVendors = vendorService.getAllVendors();

    assertEquals(3, allVendors.size());

    verify(vendorRepository, times(1)).findAll();
  }

  @Test
  @Order(6)
  void deleteVendor() {
    vendorService.deleteVendor(1L);

    verify(vendorRepository, times(1)).deleteById(1L);
  }
}