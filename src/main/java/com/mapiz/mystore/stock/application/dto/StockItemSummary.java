package com.mapiz.mystore.stock.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class StockItemSummary {
  private Integer productId;
  private String productName;
  private double quantity;
  private double salePrice;

  public void sumQuantity(double quantity) {
    this.quantity += quantity;
  }
}
