package com.mapiz.mystore.sales.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import com.mapiz.mystore.sales.infrastructure.persistence.mapper.SaleMapper;
import com.mapiz.mystore.sales.infrastructure.persistence.repository.JpaSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleRepositoryImpl implements SaleRepository {

  private final JpaSaleRepository jpaSaleRepository;

  @Override
  public Sale save(Sale model) {
    var saved = jpaSaleRepository.save(SaleMapper.INSTANCE.modelToEntity(model));
    return SaleMapper.INSTANCE.entityToModel(saved);
  }
}
