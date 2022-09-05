package com.signature.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

  @Schema(description = "Vendor name", required = true)
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("vendor_url")
  @Schema(description = "Vendor URL")
  private String vendorUrl;

  public VendorDTO(String name) {
    this.name = name;
  }
}