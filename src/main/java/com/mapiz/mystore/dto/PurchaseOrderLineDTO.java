package com.mapiz.mystore.dto;

import lombok.Data;

@Data
public class PurchaseOrderLineDTO {
    private Integer productId;
    private Integer quantity;
}
