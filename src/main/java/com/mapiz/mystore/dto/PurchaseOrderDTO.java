package com.mapiz.mystore.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderDTO {
    private Integer supplierId;
    private List<PurchaseOrderLineDTO> purchaseOrderLines;
}
