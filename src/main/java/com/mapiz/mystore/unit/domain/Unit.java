package com.mapiz.mystore.unit.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Unit {
  private Long id;
  private String name;
  private String symbol;
  private boolean isFractional;
  private boolean isBaseUnit;
  private List<Conversion> unitConversions;

  public Conversion getBaseConversion() {
    return unitConversions.stream()
        .filter(conversion -> conversion.getToUnit().isBaseUnit())
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Base conversion not found"));
  }
}
