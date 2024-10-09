package com.mapiz.mystore.sales.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import com.mapiz.mystore.sales.infrastructure.persistence.mapper.SaleMapper;
import com.mapiz.mystore.sales.infrastructure.persistence.repository.JpaSaleRepository;
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
        .map(SaleMapper.INSTANCE::entityToModel)
        .toList();
  }
}
