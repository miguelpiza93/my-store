package com.mapiz.mystore.product.application.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class ProductDetailResponse extends ProductResponse {
  private String referenceUnitSymbol;
  private boolean isFractionalUnit;
}
