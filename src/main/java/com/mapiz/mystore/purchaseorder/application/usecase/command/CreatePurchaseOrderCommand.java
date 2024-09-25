package com.mapiz.mystore.purchaseorder.application.usecase.command;

import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePurchaseOrderCommand {
    private Integer supplierId;
    private String supplierName;
    private LocalDate estimatedDeliveryDate;
    private List<PurchaseOrderLineRequest> purchaseOrderLines;
}
