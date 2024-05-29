package com.mapiz.mystore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PurchaseOrderDTO {
    private Integer productId;
    private Integer supplierId;

    @JsonProperty("unit_price")
    private Double unitPrice;

    private Integer quantity;
}
