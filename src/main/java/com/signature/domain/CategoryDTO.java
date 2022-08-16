package com.signature.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

  private long id;
  private String name;

  public CategoryDTO(String name) {
    this.name = name;
  }
}