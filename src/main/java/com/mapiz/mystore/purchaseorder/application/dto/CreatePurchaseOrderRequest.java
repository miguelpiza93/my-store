package com.mapiz.mystore.purchaseorder.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePurchaseOrderRequest {
    private Integer supplierId;
    private LocalDate estimatedDeliveryDate;

    @NotBlank
    private List<PurchaseOrderLineRequest> purchaseOrderLines;
}