package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.stock.application.dto.StockItemSummary;
import com.mapiz.mystore.stock.application.usecase.GetStockSummaryUseCase;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
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
        .quantity(item.getQuantity())
        .weightedCost(item.getPurchaseOrderLine().getCostPerBaseUnit())
        .build();
  }

  private StockItemSummary mergeStockItemSummaries(
      StockItemSummary existingItem, StockItemSummary newItem) {
    BigDecimal totalQuantity =
        BigDecimalUtils.add(existingItem.getQuantity(), newItem.getQuantity());
    BigDecimal weightedCost = calculateWeightedCost(existingItem, newItem, totalQuantity);

    existingItem.sumQuantity(newItem.getQuantity());
    existingItem.setWeightedCost(weightedCost);
    return existingItem;
  }

  private BigDecimal calculateWeightedCost(
      StockItemSummary existingItem, StockItemSummary newItem, BigDecimal totalQuantity) {
    BigDecimal existingCost =
        BigDecimalUtils.multiply(existingItem.getWeightedCost(), existingItem.getQuantity());
    BigDecimal newCost = BigDecimalUtils.multiply(newItem.getWeightedCost(), newItem.getQuantity());
    BigDecimal totalCost = BigDecimalUtils.add(existingCost, newCost);
    return BigDecimalUtils.divide(totalCost, totalQuantity);
  }
}
