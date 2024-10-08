package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderLineMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderLineRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseOrderLineRepositoryImpl implements PurchaseOrderLineRepository {

  private final JpaPurchaseOrderLineRepository jpaRepository;

  @Override
  public List<PurchaseOrderLine> saveAll(List<PurchaseOrderLine> lines) {
    var entities = lines.stream().map(getModelToEntityMapper()).toList();
    return jpaRepository.saveAll(entities).stream().map(getEntityToModelMapper()).toList();
  }

  @Override
  public Optional<PurchaseOrderLine> findById(Integer purchaseOrderLineId) {
    return jpaRepository.findById(purchaseOrderLineId).map(getEntityToModelMapper());
  }

  @Override
  public void save(PurchaseOrderLine line) {
    jpaRepository.save(getModelToEntityMapper().apply(line));
  }

  private static Function<PurchaseOrderLineEntity, PurchaseOrderLine> getEntityToModelMapper() {
    return line ->
        PurchaseOrderLineMapper.INSTANCE.entityToModel(line, new CycleAvoidingMappingContext());
  }

  private Function<PurchaseOrderLine, PurchaseOrderLineEntity> getModelToEntityMapper() {
    return line ->
        PurchaseOrderLineMapper.INSTANCE.modelToEntity(line, new CycleAvoidingMappingContext());
  }
}
