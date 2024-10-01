package com.mapiz.mystore.stock.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class StockItemResponse {
  private Integer id;
  private String productName;
  private int quantity;
  private double salePrice;
}
