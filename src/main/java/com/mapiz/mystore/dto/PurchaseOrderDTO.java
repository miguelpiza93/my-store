package com.mapiz.mystore.dto;

import lombok.Data;

@Data
public class PurchaseOrderDTO {
    private Integer productId;
    private Integer supplierId;
    private Integer quantity;
}
