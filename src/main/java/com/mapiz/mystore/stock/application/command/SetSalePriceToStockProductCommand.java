package com.mapiz.mystore.stock.application.command;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SetSalePriceToStockProductCommand {
  private int productId;
  private int vendorId;
  private int unitId;
  private BigDecimal salePrice;
}
