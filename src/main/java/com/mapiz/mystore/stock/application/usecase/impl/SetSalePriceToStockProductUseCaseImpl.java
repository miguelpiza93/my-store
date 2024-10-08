package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import com.mapiz.mystore.stock.application.exception.StockItemNotFoundException;
import com.mapiz.mystore.stock.application.usecase.SetSalePriceToStockProductUseCase;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetSalePriceToStockProductUseCaseImpl implements SetSalePriceToStockProductUseCase {

  private final StockItemRepository stockItemRepository;

  @Override
  public void accept(SetSalePriceToStockProductCommand command) {
    var stockItems = stockItemRepository.findByProductId(command.getProductId());
    if (stockItems.isEmpty()) {
      throw new StockItemNotFoundException(command.getProductId());
    }
    stockItems.forEach(stockItem -> stockItem.setSalePrice(command.getSalePrice()));
    stockItemRepository.saveAll(stockItems);
  }
}