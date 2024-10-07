package com.mapiz.mystore.stock.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import com.mapiz.mystore.stock.infrastructure.persistence.mapper.StockItemMapper;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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

  @Override
  public List<StockItem> findByProductIdIn(Collection<Integer> productIds) {
    return jpaStockItemRepository.findByProductIdIn(productIds).stream().map(getMapper()).toList();
  }

  @Override
  public List<StockItem> findAll() {
    return jpaStockItemRepository.findAll().stream().map(getMapper()).toList();
  }

  @Override
  public Optional<StockItem> findById(Integer id) {
    return jpaStockItemRepository.findById(id).map(getMapper());
  }

  @Override
  public void save(StockItem stockItem) {
    jpaStockItemRepository.save(StockItemMapper.INSTANCE.modelToEntity(stockItem));
  }

  private Function<StockItemEntity, StockItem> getMapper() {
    return item -> StockItemMapper.INSTANCE.entityToModel(item, new CycleAvoidingMappingContext());
  }
}
