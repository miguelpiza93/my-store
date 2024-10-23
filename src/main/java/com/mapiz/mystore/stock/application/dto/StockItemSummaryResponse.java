package com.mapiz.mystore.stock.application.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class StockItemSummaryResponse {
  private Integer vendorProductId;
  private Integer productId;
  private Integer vendorId;
  private String productName;
  private String vendorName;
  private BigDecimal quantity;
  private BigDecimal weightedCost;
}
