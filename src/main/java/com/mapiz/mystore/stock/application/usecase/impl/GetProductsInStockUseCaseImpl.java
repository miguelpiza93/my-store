package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.stock.application.usecase.GetProductsInStockUseCase;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductsInStockUseCaseImpl implements GetProductsInStockUseCase {

  private final StockItemRepository stockItemRepository;

  @Override
  public List<StockItem> get() {
    return stockItemRepository.findAll();
  }
}
