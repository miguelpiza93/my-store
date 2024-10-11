package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.command.RegisterSaleCommand;
import com.mapiz.mystore.sales.application.exception.NotEnoughStockException;
import com.mapiz.mystore.sales.application.mapper.SaleMapper;
import com.mapiz.mystore.sales.application.usecase.RegisterSaleUseCase;
import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.repository.UnitRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterSaleUseCaseImpl implements RegisterSaleUseCase {

  private final SaleRepository saleRepository;
  private final StockItemRepository stockItemRepository;
  private final UnitRepository unitRepository;

  @Override
  public List<Integer> apply(RegisterSaleCommand command) {
    var items = command.getItems().stream().map(SaleMapper.INSTANCE::commandItemToModel).toList();

    var stockItemsByProductId = getStockItemsByProductId(items);
    var unitsById = getUnitsById(items);

    validateStockAvailability(stockItemsByProductId, items);

    var updatedItems =
        items.stream()
            .map(
                item ->
                    enrichSale(item, stockItemsByProductId, unitsById.get(item.getUnit().getId())))
            .toList();

    var saved = saleRepository.saveAll(updatedItems);
    stockItemRepository.saveAll(
        stockItemsByProductId.values().stream().flatMap(List::stream).toList());
    return saved.stream().map(Sale::getId).toList();
  }

  private Map<Integer, Unit> getUnitsById(List<Sale> sales) {
    var unitIds = sales.stream().map(sale -> sale.getUnit().getId()).collect(Collectors.toSet());
    var units = unitRepository.findAllById(unitIds);
    return units.stream().collect(Collectors.toMap(Unit::getId, unit -> unit));
  }

  private Sale enrichSale(
      Sale sale, Map<Integer, List<StockItem>> stockItemsByProductId, Unit unit) {
    sale.setUnit(unit);
    var productStock = stockItemsByProductId.get(sale.getProduct().getId());
    var totalCost = calculateTotalCost(productStock, sale);
    var price = getSalePriceFromStock(productStock);
    sale.setCost(totalCost);
    sale.setPrice(price);
    sale.setCreatedAt(Instant.now());
    return sale;
  }

  private BigDecimal calculateTotalCost(List<StockItem> stockAvailable, Sale sale) {
    BigDecimal totalCost = BigDecimal.ZERO;
    BigDecimal saleQuantity = sale.getBaseQuantity();
    for (var productStock : stockAvailable) {
      var deductedQuantity = productStock.deductQuantity(saleQuantity);
      var cost =
          BigDecimalUtils.multiply(
              deductedQuantity, productStock.getPurchaseOrderLine().getCostPerBaseUnit());
      totalCost = BigDecimalUtils.add(totalCost, cost);
      saleQuantity = BigDecimalUtils.subtract(saleQuantity, deductedQuantity);
      if (saleQuantity.compareTo(BigDecimal.ZERO) <= 0) {
        break;
      }
    }
    return totalCost;
  }

  private void validateStockAvailability(
      Map<Integer, List<StockItem>> stockItemsByProductId, List<Sale> items) {
    List<String> insufficientStockMessages =
        items.stream()
            .filter(item -> isStockInsufficient(stockItemsByProductId, item))
            .map(item -> buildInsufficientStockMessage(stockItemsByProductId, item))
            .toList();

    if (!insufficientStockMessages.isEmpty()) {
      throw new NotEnoughStockException(String.join(", ", insufficientStockMessages));
    }
  }

  private boolean isStockInsufficient(
      Map<Integer, List<StockItem>> stockItemsByProductId, Sale item) {
    BigDecimal totalStock = getTotalStockForProduct(stockItemsByProductId, item);
    return totalStock.compareTo(item.getQuantity()) < 0;
  }

  private String buildInsufficientStockMessage(
      Map<Integer, List<StockItem>> stockItemsByProductId, Sale item) {
    BigDecimal totalStock = getTotalStockForProduct(stockItemsByProductId, item);
    return String.format(
        "Product id: %d, Wanted: %s, Have: %s",
        item.getProduct().getId(), item.getQuantity(), totalStock);
  }

  private BigDecimal getTotalStockForProduct(
      Map<Integer, List<StockItem>> stockItemsByProductId, Sale item) {
    return stockItemsByProductId.get(item.getProduct().getId()).stream()
        .map(StockItem::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal getSalePriceFromStock(List<StockItem> productStock) {
    return productStock.get(0).getPurchaseOrderLine().getProduct().getSalePrice();
  }

  private Map<Integer, List<StockItem>> getStockItemsByProductId(List<Sale> items) {
    var productIds = items.stream().map(item -> item.getProduct().getId()).toList();
    return stockItemRepository.findByProductIds(productIds).stream()
        .collect(Collectors.groupingBy(item -> item.getPurchaseOrderLine().getProduct().getId()));
  }
}
