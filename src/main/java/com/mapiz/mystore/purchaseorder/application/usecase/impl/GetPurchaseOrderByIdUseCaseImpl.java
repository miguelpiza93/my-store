package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrderByIdUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GetPurchaseOrderByIdUseCaseImpl implements GetPurchaseOrderByIdUseCase {

    private final JpaPurchaseOrderRepository purchaseOrderRepository;


    @Override
    public PurchaseOrder apply(Integer integer) {
        var entity = purchaseOrderRepository.findById(integer)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        Hibernate.unproxy(entity.getPurchaseOrderLines());
        return PurchaseOrderMapper.INSTANCE.entityToModel(entity, new CycleAvoidingMappingContext());
    }
}
