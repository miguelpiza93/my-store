package com.mapiz.mystore.unit.application.command;

import lombok.Data;

@Data
public class CreateUnitCommand {
  private String name;
  private String symbol;
  private boolean isFractional;
  private boolean isBaseUnit;
}
