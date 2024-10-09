package com.mapiz.mystore.sales.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterSaleCommandItem {
  private int productId;
  private double quantity;
  private int unitId;
}
