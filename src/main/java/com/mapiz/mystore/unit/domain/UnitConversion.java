package com.mapiz.mystore.unit.domain;

import com.mapiz.mystore.product.domain.Product;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UnitConversion {
  private Long id;
  private Unit fromUnit;
  private Unit toUnit;
  private Product product;
  private BigDecimal conversionFactor;
}
