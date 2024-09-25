package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrdersUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPurchaseOrdersUseCaseImpl implements GetPurchaseOrdersUseCase {

    private final JpaPurchaseOrderRepository purchaseOrderRepository;

    @Override
    public List<PurchaseOrder> get() {
        var result = purchaseOrderRepository.findAll();
        return result.stream().map(po -> PurchaseOrderMapper.INSTANCE.entityToModel(po, new CycleAvoidingMappingContext())).toList();
    }
}
