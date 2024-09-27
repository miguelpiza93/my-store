package com.mapiz.mystore.vendor.domain;

import com.mapiz.mystore.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProductVendor {
  private Integer id;
  private Vendor vendor;
  private Product product;
  private double price;
}
