package com.mapiz.mystore.purchaseorder.application.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderResponse {
  private Integer id;
  private String status;
  private String supplierName;
  private Instant createdAt;
  private LocalDate estimatedDeliveryDate;
  private List<PurchaseOrderLineResponse> purchaseOrderLines;
}
