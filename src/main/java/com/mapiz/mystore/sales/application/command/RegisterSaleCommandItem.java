package com.mapiz.mystore.sales.application.command;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterSaleCommandItem {
  private int vendorProductId;
  private BigDecimal quantity;
  private int unitId;
}
