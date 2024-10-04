package com.mapiz.mystore.unit.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UnitResponse {
  private Integer id;
  private String name;
  private String symbol;
  private boolean isFractional;
  private boolean isBaseUnit;
}
