package com.mapiz.mystore.mapper;

import com.mapiz.mystore.dto.PurchaseOrderLineDTO;
import com.mapiz.mystore.entity.PurchaseOrderLine;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderLineMapper {
    public PurchaseOrderLineDTO toDTO(PurchaseOrderLine purchaseOrderLine) {
        return PurchaseOrderLineDTO.builder()
                .productId(purchaseOrderLine.getProduct().getId())
                .productName(purchaseOrderLine.getProduct().getName())
                .quantity(purchaseOrderLine.getQuantity())
                .build();
    }
}
