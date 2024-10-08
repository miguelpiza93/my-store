package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.exception.PurchaseOrderNotFoundException;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.stock.application.usecase.AddProductsToStockUseCase;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddProductsToStockUseCaseImpl implements AddProductsToStockUseCase {

  private final PurchaseOrderRepository purchaseOrderRepository;
  private final StockItemRepository stockItemRepository;

  @Override
  public void accept(Integer purchaseOrderId) {
    var purchaseOrder = getPurchaseOrder(purchaseOrderId);
    var newStockItems = getNewStockItems(purchaseOrder);
    stockItemRepository.saveAll(newStockItems);
  }

  private PurchaseOrder getPurchaseOrder(Integer purchaseOrderId) {
    return purchaseOrderRepository
        .findById(purchaseOrderId)
        .orElseThrow(() -> new PurchaseOrderNotFoundException(purchaseOrderId));
  }

  private List<StockItem> getNewStockItems(PurchaseOrder purchaseOrder) {
    return purchaseOrder.getPurchaseOrderLines().stream().map(StockItem::new).toList();
  }
}
