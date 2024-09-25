package com.mapiz.mystore.purchaseorder.application.dto;

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
    private Integer quantity;
}
