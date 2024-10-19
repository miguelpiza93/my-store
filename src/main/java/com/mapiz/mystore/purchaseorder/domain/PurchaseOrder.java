package com.mapiz.mystore.purchaseorder.domain;

import com.mapiz.mystore.vendor.domain.Vendor;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrder {
  private Integer id;
  private PurchaseOrderStatus status;
  private Vendor vendor;
  private Instant createdAt;
  private BigDecimal total;
  private LocalDate estimatedDeliveryDate;
  private List<PurchaseOrderLine> purchaseOrderLines;

  public boolean wasReceived() {
    return status == PurchaseOrderStatus.RECEIVED;
  }
}
