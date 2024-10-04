package com.mapiz.mystore.unit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Conversion {
  private Long id;
  private Unit fromUnit;
  private Unit toUnit;
  private double conversionFactor;
}
