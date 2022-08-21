package com.signature.controller.v1;

import com.signature.domain.VendorDTO;
import com.signature.mapper.VendorMapper;
import com.signature.model.Vendor;
import com.signature.service.VendorService;
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
@RequestMapping("/api/v1/vendors")
public class VendorController {

  private final VendorMapper vendorMapper;
  private final VendorService vendorService;

  public VendorController(final VendorMapper vendorMapper,
                          final VendorService vendorService) {
    this.vendorMapper = vendorMapper;
    this.vendorService = vendorService;
  }

  private VendorDTO vendorToVendorDto(final Vendor vendor) {
    VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
    vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
    return vendorDTO;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VendorDTO createVendor(@RequestBody final VendorDTO vendorDTO) {
    final Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
    return vendorToVendorDto(vendorService.addVendor(vendor));
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public VendorDTO getVendor(@PathVariable final Long id) throws Exception {
    return vendorMapper.vendorToVendorDto(vendorService.getVendor(id));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<VendorDTO> getAllVendors() {
    return vendorService.getAllVendors().stream().map(this::vendorToVendorDto).collect(Collectors.toList());
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public VendorDTO updateVendor(@PathVariable final Long id,
                                @RequestBody final VendorDTO vendorDTO) throws Exception {
    final Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
    vendor.setId(id);
    return vendorMapper.vendorToVendorDto(vendorService.updateVendor(vendor));
  }

  @PatchMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public VendorDTO patchVendor(@PathVariable final Long id,
                               @RequestBody final VendorDTO vendorDTO) throws Exception {
    final Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
    vendor.setId(id);
    return vendorMapper.vendorToVendorDto(vendorService.patchVendor(vendor));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteVendor(@PathVariable final Long id) {
    vendorService.deleteVendor(id);
  }
}