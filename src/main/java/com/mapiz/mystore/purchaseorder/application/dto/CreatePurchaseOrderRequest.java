package com.mapiz.mystore.purchaseorder.application.dto;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePurchaseOrderRequest {
    private Integer supplierId;
    private String supplierName;
    private LocalDate estimatedDeliveryDate;

    @NotBlank
    private List<PurchaseOrderLine> purchaseOrderLines;
}
