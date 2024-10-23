package com.mapiz.mystore.sales.application.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SaleItemRequest {
  private int vendorProductId;
  private BigDecimal quantity;
  private int unitId;
}
