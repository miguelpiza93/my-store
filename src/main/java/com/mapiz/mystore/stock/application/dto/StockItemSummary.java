package com.mapiz.mystore.stock.application.dto;

import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class StockItemSummary {
  private Integer productId;
  private String productName;
  private BigDecimal quantity;
  private BigDecimal weightedCost;

  public void sumQuantity(BigDecimal quantity) {
    this.quantity = BigDecimalUtils.add(this.quantity, quantity);
  }
}
