package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderLineMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderLineRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseOrderLineRepositoryImpl implements PurchaseOrderLineRepository {

  private final JpaPurchaseOrderLineRepository jpaRepository;

  @Override
  public List<PurchaseOrderLine> save(List<PurchaseOrderLine> lines) {
    var entities = lines.stream().map(getMapper()).toList();
    return jpaRepository.saveAll(entities).stream()
        .map(
            line ->
                PurchaseOrderLineMapper.INSTANCE.entityToModel(
                    line, new CycleAvoidingMappingContext()))
        .toList();
  }

  private Function<PurchaseOrderLine, PurchaseOrderLineEntity> getMapper() {
    return line ->
        PurchaseOrderLineMapper.INSTANCE.modelToEntity(line, new CycleAvoidingMappingContext());
  }
}
