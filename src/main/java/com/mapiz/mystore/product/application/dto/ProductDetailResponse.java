package com.mapiz.mystore.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProductDetailResponse {
  private Integer id;
  private String name;
  private String description;
  private String referenceUnit;
}
