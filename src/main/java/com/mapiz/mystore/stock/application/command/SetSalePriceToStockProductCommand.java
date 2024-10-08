package com.mapiz.mystore.stock.application.command;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SetSalePriceToStockProductCommand {
  private int productId;
  private int salePrice;
}
