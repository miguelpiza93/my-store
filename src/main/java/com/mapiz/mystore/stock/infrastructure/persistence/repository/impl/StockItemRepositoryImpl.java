package com.mapiz.mystore.stock.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import com.mapiz.mystore.stock.infrastructure.persistence.mapper.StockItemMapper;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockItemRepositoryImpl implements StockItemRepository {

  private final StockItemMapper stockItemMapper;
  private final JpaStockItemRepository jpaStockItemRepository;

  @Override
  public void saveAll(List<StockItem> stockItems) {
    var stockItemEntities = stockItems.stream().map(stockItemMapper::modelToEntity).toList();
    jpaStockItemRepository.saveAll(stockItemEntities);
  }

  @Override
  public List<StockItem> findAll() {
    return jpaStockItemRepository.findAll().stream().map(stockItemMapper::entityToModel).toList();
  }

  @Override
  public Optional<StockItem> findById(Integer id) {
    return jpaStockItemRepository.findById(id).map(stockItemMapper::entityToModel);
  }

  @Override
  public void save(StockItem stockItem) {
    jpaStockItemRepository.save(stockItemMapper.modelToEntity(stockItem));
  }

  @Override
  public List<StockItem> findByVendorProductIds(Collection<Integer> ids) {
    return jpaStockItemRepository.findByVendorProductIds(ids).stream()
        .map(stockItemMapper::entityToModel)
        .toList();
  }

  @Override
  public List<StockItem> findAllAvailable() {
    return jpaStockItemRepository.findAllAvailable().stream()
        .map(stockItemMapper::entityToModel)
        .toList();
  }
}
