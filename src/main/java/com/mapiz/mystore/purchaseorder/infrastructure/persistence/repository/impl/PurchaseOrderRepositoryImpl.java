package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

  private final PurchaseOrderMapper PurchaseOrderMapper;
  private final JpaPurchaseOrderRepository jpaRepository;

  @Override
  public PurchaseOrder save(PurchaseOrder purchaseOrder) {
    var entity = PurchaseOrderMapper.modelToEntity(purchaseOrder);
    return PurchaseOrderMapper.entityToModel(jpaRepository.save(entity));
  }

  @Override
  public Optional<PurchaseOrder> findById(Integer id) {
    var res = jpaRepository.findById(id);
    return res.map(PurchaseOrderMapper::entityToModel);
  }

  @Override
  public List<PurchaseOrder> findAll() {
    return jpaRepository.findAll().stream().map(PurchaseOrderMapper::entityToModel).toList();
  }
}
