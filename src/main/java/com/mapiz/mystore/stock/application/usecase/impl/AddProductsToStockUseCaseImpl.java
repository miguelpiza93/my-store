package com.mapiz.mystore.stock.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.exception.PurchaseOrderNotFoundException;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.stock.application.usecase.AddProductsToStockUseCase;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddProductsToStockUseCaseImpl implements AddProductsToStockUseCase {

  private final PurchaseOrderRepository purchaseOrderRepository;
  private final StockItemRepository stockItemRepository;

  @Override
  @Transactional
  public void accept(Integer purchaseOrderId) {
    var purchaseOrder = getPurchaseOrder(purchaseOrderId);

    // Obtener y actualizar el stock existente
    var existingStockItems = getExistingStockItemsByProductId(purchaseOrder);
    incrementStockForExistingItems(purchaseOrder, existingStockItems);

    // Generar nuevos elementos de stock
    var newStockItems = getNewStockItems(purchaseOrder, existingStockItems);

    // Combinar y guardar todos los elementos de stock
    var allStockItems =
        Stream.concat(existingStockItems.values().stream(), newStockItems.stream())
            .collect(Collectors.toList());

    stockItemRepository.saveAll(allStockItems);
  }

  private void incrementStockForExistingItems(
      PurchaseOrder purchaseOrder, Map<Integer, StockItem> existingStockItemsByProductId) {
    if (existingStockItemsByProductId.isEmpty()) {
      return;
    }

    purchaseOrder.getPurchaseOrderLines().stream()
        .map(
            purchaseOrderLine ->
                Map.entry(
                    purchaseOrderLine,
                    existingStockItemsByProductId.get(purchaseOrderLine.getProduct().getId())))
        .filter(entry -> entry.getValue() != null)
        .forEach(entry -> incrementStockItem(entry.getKey(), entry.getValue()));
  }

  private void incrementStockItem(PurchaseOrderLine purchaseOrderLine, StockItem stockItem) {
    stockItem.incrementQuantity(purchaseOrderLine);
  }

  private PurchaseOrder getPurchaseOrder(Integer purchaseOrderId) {
    return purchaseOrderRepository
        .findById(purchaseOrderId)
        .orElseThrow(() -> new PurchaseOrderNotFoundException(purchaseOrderId));
  }

  private List<StockItem> getNewStockItems(
      PurchaseOrder purchaseOrder, Map<Integer, StockItem> existingStockItemsByProductId) {
    return purchaseOrder.getPurchaseOrderLines().stream()
        .filter(
            purchaseOrderLine ->
                !existingStockItemsByProductId.containsKey(purchaseOrderLine.getProduct().getId()))
        .map(StockItem::new)
        .toList();
  }

  private Map<Integer, StockItem> getExistingStockItemsByProductId(PurchaseOrder purchaseOrder) {
    var productIdsToIncrementStock = getProductIdsToIncrementStock(purchaseOrder);
    return stockItemRepository.findByProductIdIn(productIdsToIncrementStock).stream()
        .collect(
            Collectors.toMap(stockItem -> stockItem.getProduct().getId(), stockItem -> stockItem));
  }

  private Collection<Integer> getProductIdsToIncrementStock(PurchaseOrder purchaseOrder) {
    return purchaseOrder.getPurchaseOrderLines().stream()
        .map(purchaseOrderLine -> purchaseOrderLine.getProduct().getId())
        .collect(Collectors.toSet());
  }
}
