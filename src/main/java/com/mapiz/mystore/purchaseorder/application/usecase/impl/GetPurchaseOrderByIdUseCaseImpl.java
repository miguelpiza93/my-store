package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrderByIdUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GetPurchaseOrderByIdUseCaseImpl implements GetPurchaseOrderByIdUseCase {

    private final JpaPurchaseOrderRepository purchaseOrderRepository;


    @Override
    public PurchaseOrder apply(Integer integer) {
        return purchaseOrderRepository.findById(integer).map(PurchaseOrderMapper.INSTANCE::entityToModel)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }
}
