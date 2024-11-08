package com.mapiz.mystore.vendor.application.dto;

import com.mapiz.mystore.product.application.dto.ProductDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class VendorProductResponse {
  private Integer id;
  private ProductDetailResponse product;
  private double price;
}
