package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final JpaPurchaseOrderRepository jpaRepository;

    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        var entity = PurchaseOrderMapper.INSTANCE.modelToEntity(purchaseOrder);
        return PurchaseOrderMapper.INSTANCE.entityToModel(jpaRepository.save(entity));
    }
}
