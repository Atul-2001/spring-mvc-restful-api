package com.signature.service;

import com.signature.model.Vendor;

import java.util.List;

public interface VendorService {

  Vendor addVendor(Vendor vendor);

  Vendor updateVendor(Vendor vendor) throws Exception;

  Vendor patchVendor(Vendor vendor) throws Exception;

  Vendor getVendor(long vendorId) throws Exception;

  List<Vendor> getAllVendors();

  void deleteVendor(long vendorId);
}