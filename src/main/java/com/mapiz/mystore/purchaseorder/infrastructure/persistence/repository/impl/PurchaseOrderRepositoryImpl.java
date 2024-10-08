package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    var res = jpaRepository.findById(id);
    return res.map(
        entity ->
            PurchaseOrderMapper.INSTANCE.entityToModel(entity, new CycleAvoidingMappingContext()));
  }

  @Override
  public List<PurchaseOrder> findAll() {
    return jpaRepository.findAll().stream()
        .map(
            entity ->
                PurchaseOrderMapper.INSTANCE.entityToModel(
                    entity, new CycleAvoidingMappingContext()))
        .toList();
  }
}
