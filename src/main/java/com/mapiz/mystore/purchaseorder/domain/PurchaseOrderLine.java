package com.mapiz.mystore.purchaseorder.domain;

import com.mapiz.mystore.product.domain.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderLine {
    private Integer id;
    private Product product;
    private Integer quantity;
    private Double unitPrice;
    private PurchaseOrder purchaseOrder;
}
