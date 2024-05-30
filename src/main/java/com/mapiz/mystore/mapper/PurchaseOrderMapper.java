package com.mapiz.mystore.mapper;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.entity.PurchaseOrder;
import org.springframework.stereotype.Component;


@Component
public class PurchaseOrderMapper {

    public PurchaseOrderDTO toDTO(PurchaseOrder purchaseOrder) {
        return PurchaseOrderDTO.builder()
                .id(purchaseOrder.getId())
                .supplierId(purchaseOrder.getSupplier().getId())
                .supplierName(purchaseOrder.getSupplier().getName())
                .createdAt(purchaseOrder.getCreatedAt())
                .build();
    }
}
