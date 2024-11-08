package com.mapiz.mystore.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@Data
public class ProductResponse {
  private Integer id;
  private String name;
  private String description;
}
