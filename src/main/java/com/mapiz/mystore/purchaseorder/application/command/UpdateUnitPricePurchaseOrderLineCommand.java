package com.mapiz.mystore.purchaseorder.application.command;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUnitPricePurchaseOrderLineCommand {
  private Integer purchaseOrderId;
  private Integer purchaseOrderLineId;
  private BigDecimal unitPrice;
}
