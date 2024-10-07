package com.mapiz.mystore.stock.domain.repository;

import com.mapiz.mystore.stock.domain.StockItem;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StockItemRepository {
  void saveAll(List<StockItem> stockItems);

  List<StockItem> findByProductIdIn(Collection<Integer> productIds);

  List<StockItem> findAll();

  Optional<StockItem> findById(Integer id);

  void save(StockItem stockItem);
}
