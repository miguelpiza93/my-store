package com.mapiz.mystore.purchaseorder.domain;

import com.mapiz.mystore.vendor.domain.Vendor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class PurchaseOrder {
    private Integer id;
    private Vendor vendor;
    private Instant createdAt;
    private LocalDate estimatedDeliveryDate;
    private List<PurchaseOrderLine> purchaseOrderLines;
}
