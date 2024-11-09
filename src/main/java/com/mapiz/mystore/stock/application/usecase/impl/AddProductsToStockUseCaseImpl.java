package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import com.mapiz.mystore.stock.application.usecase.AddProductsToStockUseCase;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddProductsToStockUseCaseImpl implements AddProductsToStockUseCase {

  private final PurchaseOrderLineRepository purchaseOrderLineRepository;
  private final StockItemRepository stockItemRepository;

  @Override
  public void accept(Integer purchaseOrderId) {
    var purchaseOrderLines = getPurchaseOrderLines(purchaseOrderId);
    var newStockItems = getNewStockItems(purchaseOrderLines);
    stockItemRepository.saveAll(newStockItems);
  }

  private List<PurchaseOrderLine> getPurchaseOrderLines(Integer purchaseOrderId) {
    return purchaseOrderLineRepository.findByPurchaseOrderId(purchaseOrderId);
  }

  private List<StockItem> getNewStockItems(List<PurchaseOrderLine> purchaseOrderLines) {
    return purchaseOrderLines.stream().map(StockItem::new).toList();
  }
}
