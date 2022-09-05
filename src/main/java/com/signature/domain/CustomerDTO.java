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
public class CustomerDTO {

  @Schema(description = "Customer first name", required = true)
  private String firstName;

  @Schema(description = "Customer last name", required = true)
  private String lastName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("customer_url")
  @Schema(description = "Customer URL")
  private String customerUrl;

  public CustomerDTO(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}