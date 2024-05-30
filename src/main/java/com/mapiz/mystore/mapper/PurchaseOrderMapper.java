package com.mapiz.mystore.mapper;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PurchaseOrderMapper {

    @Autowired
    private PurchaseOrderLineMapper purchaseOrderLineMapper;

    public PurchaseOrderDTO toDTO(PurchaseOrder purchaseOrder) {
        return this.toDTO(purchaseOrder, false);
    }

    public PurchaseOrderDTO toFullDTO(PurchaseOrder purchaseOrder) {
        return this.toDTO(purchaseOrder, true);
    }

    private PurchaseOrderDTO toDTO(PurchaseOrder purchaseOrder, boolean includeLines) {
        PurchaseOrderDTO dto = PurchaseOrderDTO.builder()
                .id(purchaseOrder.getId())
                .supplierId(purchaseOrder.getSupplier().getId())
                .supplierName(purchaseOrder.getSupplier().getName())
                .createdAt(purchaseOrder.getCreatedAt())
                .build();

        if(includeLines){
            dto.setPurchaseOrderLines(purchaseOrder.getPurchaseOrderLines().stream().map(
                    purchaseOrderLineMapper::toDTO
            ).toList());
        }
        return dto;
    }
}
