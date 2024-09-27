package com.mapiz.mystore.vendor.application.dto;

import com.mapiz.mystore.product.application.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ProductVendorResponse {
  private Integer id;
  private ProductResponse product;
  private double price;
}
