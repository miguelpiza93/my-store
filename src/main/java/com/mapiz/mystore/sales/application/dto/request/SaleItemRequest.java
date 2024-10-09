package com.mapiz.mystore.sales.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SaleItemRequest {
  private int productId;
  private int quantity;
  private int unitId;
}
