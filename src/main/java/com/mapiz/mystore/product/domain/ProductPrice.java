package com.mapiz.mystore.product.domain;

import com.mapiz.mystore.unit.domain.Unit;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProductPrice {
  private Integer id;
  private Product product;
  private Unit unit;
  private BigDecimal salePrice;

  @Override
  public String toString() {
    return "ProductPrice{" + ", unit=" + unit + ", salePrice=" + salePrice + '}';
  }
}
