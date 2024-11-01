package com.mapiz.mystore.sales.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.sales.domain.SaleLine;
import com.mapiz.mystore.sales.domain.repository.SaleLineRepository;
import com.mapiz.mystore.sales.infrastructure.persistence.mapper.SaleLineMapper;
import com.mapiz.mystore.sales.infrastructure.persistence.repository.JpaSaleLineRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleLineRepositoryImpl implements SaleLineRepository {

  private final SaleLineMapper SaleLineMapper;
  private final JpaSaleLineRepository jpaSaleLineRepository;

  @Override
  public List<SaleLine> saveAll(List<SaleLine> models) {
    var entities = models.stream().map(SaleLineMapper::modelToEntity).toList();
    return jpaSaleLineRepository.saveAll(entities).stream()
        .map(SaleLineMapper::entityToModel)
        .toList();
  }
}
