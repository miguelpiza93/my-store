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

  private final SaleMapper saleMapper;
  private final JpaSaleRepository jpaSaleRepository;

  @Override
  public Sale save(Sale model) {
    var saved = jpaSaleRepository.save(saleMapper.modelToEntity(model));
    return saleMapper.entityToModel(saved);
  }

  @Override
  public List<Sale> findAll() {
    return jpaSaleRepository.findAll().stream().map(saleMapper::entityToModel).toList();
  }
}
