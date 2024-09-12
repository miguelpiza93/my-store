package com.mapiz.mystore.purchaseorder.application.usecase.command;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreatePurchaseOrderCommand {
    private Integer supplierId;
    private String supplierName;
    private LocalDate estimatedDeliveryDate;
    private List<PurchaseOrderLine> purchaseOrderLines;
}
