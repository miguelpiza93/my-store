package com.mapiz.mystore.unit.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("isFractional")
  private boolean isFractional;

  @JsonProperty("isBaseUnit")
  private boolean isBaseUnit;
}
