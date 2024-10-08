package com.mapiz.mystore.purchaseorder.application.dto;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderLineRequest {
  private Integer productId;

  @Positive private BigDecimal quantity;

  @Positive private BigDecimal unitPrice;
}
