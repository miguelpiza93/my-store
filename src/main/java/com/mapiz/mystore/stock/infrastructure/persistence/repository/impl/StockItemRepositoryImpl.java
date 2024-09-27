package com.mapiz.mystore.stock.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import com.mapiz.mystore.stock.infrastructure.persistence.mapper.StockItemMapper;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockItemRepositoryImpl implements StockItemRepository {

  private final JpaStockItemRepository jpaStockItemRepository;

  @Override
  public void saveAll(List<StockItem> stockItems) {
    var stockItemEntities =
        stockItems.stream().map(StockItemMapper.INSTANCE::modelToEntity).toList();
    jpaStockItemRepository.saveAll(stockItemEntities);
  }
}