package com.mapiz.mystore.stock.domain.repository;

import com.mapiz.mystore.stock.domain.StockItem;
import java.util.List;

public interface StockItemRepository {
  void saveAll(List<StockItem> stockItems);
}
