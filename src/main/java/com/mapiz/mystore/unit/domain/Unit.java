package com.mapiz.mystore.unit.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Unit {
  private Integer id;
  private String name;
  private String symbol;
  private boolean isFractional;
  private boolean isBaseUnit;
  private List<UnitConversion> unitConversions;

  public UnitConversion getBaseConversion() {
    if (isBaseUnit) {
      return UnitConversion.builder().conversionFactor(BigDecimal.ONE).build();
    }

    return unitConversions.stream()
        .filter(conversion -> conversion.getToUnit().isBaseUnit())
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Base conversion not found"));
  }

  @Override
  public String toString() {
    return "Unit{" + "id=" + id + ", name='" + name + '\'' + ", symbol='" + symbol + '\'' + '}';
  }
}
