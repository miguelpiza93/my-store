package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderLineMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderLineRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseOrderLineRepositoryImpl implements PurchaseOrderLineRepository {

  private final PurchaseOrderLineMapper purchaseOrderLineMapper;
  private final JpaPurchaseOrderLineRepository jpaRepository;

  @Override
  public List<PurchaseOrderLine> saveAll(List<PurchaseOrderLine> lines) {
    var entities = lines.stream().map(purchaseOrderLineMapper::modelToEntity).toList();
    return jpaRepository.saveAll(entities).stream()
        .map(purchaseOrderLineMapper::entityToModel)
        .toList();
  }

  @Override
  public Optional<PurchaseOrderLine> findById(Integer purchaseOrderLineId) {
    return jpaRepository.findById(purchaseOrderLineId).map(purchaseOrderLineMapper::entityToModel);
  }

  @Override
  public void save(PurchaseOrderLine line) {
    jpaRepository.save(purchaseOrderLineMapper.modelToEntity(line));
  }

  @Override
  public List<PurchaseOrderLine> findByPurchaseOrderId(Integer purchaseOrderId) {
    return jpaRepository.findByPurchaseOrderId(purchaseOrderId).stream()
        .map(purchaseOrderLineMapper::entityToModel)
        .toList();
  }
}
