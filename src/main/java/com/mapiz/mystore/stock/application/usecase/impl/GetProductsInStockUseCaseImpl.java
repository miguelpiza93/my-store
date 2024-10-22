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

    for (var stock : stockAvailable) {
      var product = stock.getPurchaseOrderLine().getVendorProduct().getProduct();
      Integer productId = product.getId();
      StockItemSummary summary;
      if (!result.containsKey(productId)) {
        summary = new StockItemSummary(stock);
      } else {
        summary = result.get(productId);
        summary.addStock(stock);
      }
      result.put(productId, summary);
    }

    return result.values();
  }
}
