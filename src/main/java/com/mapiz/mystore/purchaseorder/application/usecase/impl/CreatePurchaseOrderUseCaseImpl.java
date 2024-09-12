package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.usecase.CreatePurchaseOrderUseCase;
import com.mapiz.mystore.purchaseorder.application.usecase.command.CreatePurchaseOrderCommand;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CreatePurchaseOrderUseCaseImpl implements CreatePurchaseOrderUseCase {

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final VendorRepository vendorRepository;

    @Override
    public PurchaseOrder apply(CreatePurchaseOrderCommand command) {
        Vendor vendor = vendorRepository.findById(command.getSupplierId())
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id " + command.getSupplierId()));

        PurchaseOrder purchaseOrder = buildPurchaseOrder(command, vendor);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    private PurchaseOrder buildPurchaseOrder(CreatePurchaseOrderCommand purchaseOrderDTO, Vendor supplier) {
        return PurchaseOrder.builder()
                .createdAt(Instant.now())
                .vendor(supplier)
                .estimatedDeliveryDate(purchaseOrderDTO.getEstimatedDeliveryDate())
                .purchaseOrderLines(purchaseOrderDTO.getPurchaseOrderLines())
                .build();
    }
}
