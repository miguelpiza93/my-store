package com.mapiz.mystore.vendor.domain;

import com.mapiz.mystore.product.domain.Product;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class VendorProduct {
  private Integer id;
  private Vendor vendor;
  private Product product;
  private BigDecimal price;
}
