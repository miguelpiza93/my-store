package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final JpaPurchaseOrderRepository jpaRepository;

    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        var context = new CycleAvoidingMappingContext();
        var entity = PurchaseOrderMapper.INSTANCE.modelToEntity(purchaseOrder, context);
        return PurchaseOrderMapper.INSTANCE.entityToModel(jpaRepository.save(entity), context);
    }

    @Override
    public Optional<PurchaseOrder> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(entity -> PurchaseOrderMapper.INSTANCE.entityToModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity -> PurchaseOrderMapper.INSTANCE.entityToModel(entity, new CycleAvoidingMappingContext()))
                .toList();
    }
}
