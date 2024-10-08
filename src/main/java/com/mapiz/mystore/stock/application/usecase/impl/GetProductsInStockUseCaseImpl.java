package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.stock.application.dto.StockItemSummary;
import com.mapiz.mystore.stock.application.usecase.GetStockSummaryUseCase;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductsInStockUseCaseImpl implements GetStockSummaryUseCase {

  private final StockItemRepository stockItemRepository;

  @Override
  public Collection<StockItemSummary> get() {
    return stockItemRepository.findAllAvailable().stream()
        .collect(
            Collectors.toMap(
                item -> item.getPurchaseOrderLine().getProduct().getId(),
                this::createStockItemSummary,
                (existingItem, newItem) -> {
                  existingItem.sumQuantity(newItem.getQuantity());
                  return existingItem;
                }))
        .values();
  }

  private StockItemSummary createStockItemSummary(StockItem item) {
    return StockItemSummary.builder()
        .productId(item.getPurchaseOrderLine().getProduct().getId())
        .productName(item.getPurchaseOrderLine().getProduct().getName())
        .salePrice(item.getSalePrice())
        .quantity(item.getQuantity())
        .build();
  }
}
