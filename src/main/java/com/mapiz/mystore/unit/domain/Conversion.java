package com.mapiz.mystore.unit.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Conversion {
  private Long id;
  private Unit fromUnit;
  private Unit toUnit;
  private BigDecimal conversionFactor;
}
