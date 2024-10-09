package com.mapiz.mystore.unit.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Unit {
  private Integer id;
  private String name;
  private String symbol;
  private boolean isFractional;
  private boolean isBaseUnit;
  private List<Conversion> unitConversions;

  public Conversion getBaseConversion() {
    if (isBaseUnit) {
      return Conversion.builder().conversionFactor(BigDecimal.ONE).build();
    }

    return unitConversions.stream()
        .filter(conversion -> conversion.getToUnit().isBaseUnit())
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Base conversion not found"));
  }
}
