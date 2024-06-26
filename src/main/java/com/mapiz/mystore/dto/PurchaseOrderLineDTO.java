package com.mapiz.mystore.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderLineDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
}
