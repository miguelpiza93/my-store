package com.mapiz.mystore.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class PurchaseOrderDTO {
    private Integer id;
    private Integer supplierId;
    private String supplierName;
    private Instant createdAt;
    private List<PurchaseOrderLineDTO> purchaseOrderLines;
}
