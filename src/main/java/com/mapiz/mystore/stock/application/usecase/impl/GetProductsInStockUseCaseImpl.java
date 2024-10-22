package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.stock.application.usecase.GetStockSummaryUseCase;
import com.mapiz.mystore.stock.domain.StockItemSummary;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductsInStockUseCaseImpl implements GetStockSummaryUseCase {

  private final StockItemRepository stockItemRepository;

  @Override
  public Collection<StockItemSummary> get() {
    var result = new ConcurrentHashMap<Integer, StockItemSummary>();
    var stockAvailable = stockItemRepository.findAllAvailable();

    stockAvailable.forEach(
        stock -> {
          var vendorProduct = stock.getPurchaseOrderLine().getVendorProduct();
          Integer vendorProductId = vendorProduct.getId();
          result
              .computeIfAbsent(vendorProductId, id -> new StockItemSummary(vendorProduct))
              .addStock(stock);
        });

    return result.values();
  }
}
