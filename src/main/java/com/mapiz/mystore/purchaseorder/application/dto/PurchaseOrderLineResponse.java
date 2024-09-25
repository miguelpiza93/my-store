package com.mapiz.mystore.purchaseorder.application.dto;

import com.mapiz.mystore.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderLineResponse {
    private Integer id;
    private Product product;
    private Integer quantity;
    private Double unitPrice;
}
