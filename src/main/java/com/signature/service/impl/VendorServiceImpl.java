package com.signature.service.impl;

import com.signature.exception.ResourceNotFoundException;
import com.signature.exception.ResourceUpdateFailedException;
import com.signature.model.Vendor;
import com.signature.repository.VendorRepository;
import com.signature.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class VendorServiceImpl implements VendorService {

  private final VendorRepository vendorRepository;

  public VendorServiceImpl(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  @Override
  public Vendor addVendor(Vendor vendor) {
    return vendorRepository.save(vendor);
  }

  @Override
  public Vendor updateVendor(Vendor vendor) throws Exception {
    final Integer rowAffected = vendorRepository.update(vendor);
    if (rowAffected == 0) {
      throw new ResourceUpdateFailedException("Failed to update vendor with id " + vendor.getId());
    }
    return getVendor(vendor.getId());
  }

  @Override
  public Vendor patchVendor(Vendor vendor) throws Exception {
    final Vendor existingVendor = getVendor(vendor.getId());
    if (existingVendor == null) {
      throw new ResourceNotFoundException("Vendor with id " + vendor.getId() + " not found");
    } else {
      if (vendor.getName() != null) {
        existingVendor.setName(vendor.getName());
      }
      final Integer rowAffected = vendorRepository.update(existingVendor);
      if (rowAffected == 0) {
        throw new ResourceUpdateFailedException("Failed to patch vendor with id " + vendor.getId());
      }
      return getVendor(vendor.getId());
    }
  }

  @Override
  public Vendor getVendor(long vendorId) throws Exception {
    try {
      return vendorRepository.findById(vendorId).orElseThrow();
    } catch (final NoSuchElementException ignored) {
      throw new ResourceNotFoundException("Vendor with id " + vendorId + " not found");
    }
  }

  @Override
  public List<Vendor> getAllVendors() {
    return vendorRepository.findAll();
  }

  @Override
  public void deleteVendor(long vendorId) {
    vendorRepository.deleteById(vendorId);
  }
}