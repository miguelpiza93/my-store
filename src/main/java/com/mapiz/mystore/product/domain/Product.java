package com.mapiz.mystore.product.domain;

import com.mapiz.mystore.unit.domain.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Product {
  private Integer id;
  private String name;
  private String description;
  private Unit referenceUnit;
}
