package com.mapiz.mystore.sales.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import com.mapiz.mystore.sales.infrastructure.persistence.mapper.SaleMapper;
import com.mapiz.mystore.sales.infrastructure.persistence.repository.JpaSaleRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleRepositoryImpl implements SaleRepository {

  private final JpaSaleRepository jpaSaleRepository;

  @Override
  public List<Sale> saveAll(List<Sale> models) {
    var entities = models.stream().map(SaleMapper.INSTANCE::modelToEntity).toList();
    return jpaSaleRepository.saveAll(entities).stream()
        .map(entity -> SaleMapper.INSTANCE.entityToModel(entity, new CycleAvoidingMappingContext()))
        .toList();
  }
}
