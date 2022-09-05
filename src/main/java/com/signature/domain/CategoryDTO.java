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
public class CategoryDTO {

  @Schema(description = "Category name", required = true)
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("category_url")
  @Schema(description = "Category URL")
  private String categoryUrl;

  public CategoryDTO(String name) {
    this.name = name;
  }
}