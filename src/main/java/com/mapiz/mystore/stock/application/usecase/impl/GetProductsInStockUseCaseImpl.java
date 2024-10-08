package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.stock.application.dto.StockItemSummary;
import com.mapiz.mystore.stock.application.usecase.GetStockSummaryUseCase;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
                this::mergeStockItemSummaries))
        .values();
  }

  private StockItemSummary createStockItemSummary(StockItem item) {
    return StockItemSummary.builder()
        .productId(item.getPurchaseOrderLine().getProduct().getId())
        .productName(item.getPurchaseOrderLine().getProduct().getName())
        .salePrice(item.getSalePrice())
        .quantity(item.getQuantity())
        .weightedCost(item.getPurchaseOrderLine().getCostPerBaseUnit())
        .build();
  }

  private StockItemSummary mergeStockItemSummaries(
      StockItemSummary existingItem, StockItemSummary newItem) {
    BigDecimal totalQuantity = existingItem.getQuantity().add(newItem.getQuantity());
    BigDecimal weightedCost = calculateWeightedCost(existingItem, newItem, totalQuantity);

    existingItem.sumQuantity(newItem.getQuantity());
    existingItem.setWeightedCost(weightedCost);
    return existingItem;
  }

  private BigDecimal calculateWeightedCost(
      StockItemSummary existingItem, StockItemSummary newItem, BigDecimal totalQuantity) {
    BigDecimal existingCost = existingItem.getWeightedCost().multiply(existingItem.getQuantity());
    BigDecimal newCost = newItem.getWeightedCost().multiply(newItem.getQuantity());
    return existingCost.add(newCost).divide(totalQuantity, RoundingMode.HALF_UP);
  }
}
