package com.mapiz.mystore.unit.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUnitRequest {
  @NotBlank private String name;
  @NotBlank private String symbol;

  @JsonProperty("isFractional")
  private boolean isFractional;

  @JsonProperty("isBaseUnit")
  private boolean isBaseUnit;
}
