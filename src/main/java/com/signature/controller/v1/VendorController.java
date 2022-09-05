package com.signature.controller.v1;

import com.signature.domain.VendorDTO;
import com.signature.mapper.VendorMapper;
import com.signature.model.Vendor;
import com.signature.service.VendorService;
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
@RequestMapping("/api/v1/vendors")
@Tag(name = "Vendor Controller", description = "Vendor API")
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
  @Operation(summary = "Create a new vendor")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Vendor created",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = VendorDTO.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
          @ApiResponse(responseCode = "409", description = "Vendor already exists", content = @Content)
  })
  public VendorDTO createVendor(@RequestBody final VendorDTO vendorDTO) {
    final Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
    return vendorToVendorDto(vendorService.addVendor(vendor));
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get a vendor by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Vendor found",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = VendorDTO.class))),
          @ApiResponse(responseCode = "404", description = "Vendor not found", content = @Content)
  })
  public VendorDTO getVendor(@PathVariable final Long id) throws Exception {
    return vendorMapper.vendorToVendorDto(vendorService.getVendor(id));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get all vendors")
  @ApiResponse(responseCode = "200", description = "Found all vendors",
          content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = VendorDTO.class))))
  public List<VendorDTO> getAllVendors() {
    return vendorService.getAllVendors().stream().map(this::vendorToVendorDto).collect(Collectors.toList());
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update an existing vendor")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Vendor updated",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = VendorDTO.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
          @ApiResponse(responseCode = "404", description = "Vendor not found", content = @Content)
  })
  public VendorDTO updateVendor(@PathVariable final Long id,
                                @RequestBody final VendorDTO vendorDTO) throws Exception {
    final Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
    vendor.setId(id);
    return vendorMapper.vendorToVendorDto(vendorService.updateVendor(vendor));
  }

  @PatchMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Patch an existing vendor")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Vendor patched",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = VendorDTO.class))),
          @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
          @ApiResponse(responseCode = "404", description = "Vendor not found", content = @Content)
  })
  public VendorDTO patchVendor(@PathVariable final Long id,
                               @RequestBody final VendorDTO vendorDTO) throws Exception {
    final Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
    vendor.setId(id);
    return vendorMapper.vendorToVendorDto(vendorService.patchVendor(vendor));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Delete a vendor")
  @ApiResponse(responseCode = "200", description = "Vendor deleted")
  public void deleteVendor(@PathVariable final Long id) {
    vendorService.deleteVendor(id);
  }
}