package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.product.domain.repository.UnitStockItemRepository;
import com.mapiz.mystore.sales.application.command.RegisterSaleCommand;
import com.mapiz.mystore.sales.application.exception.NotEnoughStockException;
import com.mapiz.mystore.sales.application.mapper.SaleLineMapper;
import com.mapiz.mystore.sales.application.usecase.RegisterSaleUseCase;
import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.domain.SaleLine;
import com.mapiz.mystore.sales.domain.repository.SaleLineRepository;
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
  private final SaleLineRepository saleLineRepository;
  private final StockItemRepository stockItemRepository;
  private final UnitStockItemRepository productPriceRepository;
  private final UnitRepository unitRepository;

  @Override
  public Integer apply(RegisterSaleCommand command) {
    var savedSale = saveSale();
    saveLines(command, savedSale);
    return 0;
  }

  private Sale saveSale() {
    return saleRepository.save(Sale.builder().createdAt(Instant.now()).status("CLOSED").build());
  }

  private void saveLines(RegisterSaleCommand command, Sale savedSale) {
    var lines =
        command.getItems().stream().map(SaleLineMapper.INSTANCE::commandItemToModel).toList();
    var stockItemsByVendorProductId = getStockItemsByVendorProductId(lines);
    var unitsById = getUnitsById(lines);

    validateStockAvailability(stockItemsByVendorProductId, lines);
    var linesToSave = getLinesToSave(lines, stockItemsByVendorProductId, unitsById, savedSale);
    saleLineRepository.saveAll(linesToSave);
    stockItemRepository.saveAll(
        stockItemsByVendorProductId.values().stream().flatMap(List::stream).toList());
  }

  private List<SaleLine> getLinesToSave(
      List<SaleLine> lines,
      Map<Integer, List<StockItem>> stockItemsByVendorProductId,
      Map<Integer, Unit> unitsById,
      Sale savedSale) {
    return lines.stream()
        .map(
            line ->
                enrichSale(
                    line,
                    stockItemsByVendorProductId,
                    unitsById.get(line.getVendorProductVariant().getUnit().getId()),
                    savedSale))
        .toList();
  }

  private Map<Integer, Unit> getUnitsById(List<SaleLine> lines) {
    var unitIds =
        lines.stream()
            .map(line -> line.getVendorProductVariant().getUnit().getId())
            .collect(Collectors.toSet());
    var units = unitRepository.findAllById(unitIds);
    return units.stream().collect(Collectors.toMap(Unit::getId, unit -> unit));
  }

  private SaleLine enrichSale(
      SaleLine line,
      Map<Integer, List<StockItem>> stockItemsByVendorProductId,
      Unit unit,
      Sale savedSale) {
    line.getVendorProductVariant().setUnit(unit);
    var vendorProductStock =
        stockItemsByVendorProductId.get(line.getVendorProductVariant().getVendorProduct().getId());
    var totalCost = calculateTotalCost(vendorProductStock, line);
    var price = getSalePriceFromStock(vendorProductStock, unit);
    line.setCost(totalCost);
    line.setUnitPrice(price);
    line.setSale(savedSale);
    return line;
  }

  private BigDecimal calculateTotalCost(List<StockItem> stockAvailable, SaleLine line) {
    BigDecimal totalCost = BigDecimal.ZERO;
    BigDecimal lineQuantity = line.getBaseQuantity();
    for (var productStock : stockAvailable) {
      var deductedQuantity = productStock.deductQuantity(lineQuantity);
      var cost =
          BigDecimalUtils.multiply(
              deductedQuantity, productStock.getPurchaseOrderLine().getCostPerBaseUnit());
      totalCost = BigDecimalUtils.add(totalCost, cost);
      lineQuantity = BigDecimalUtils.subtract(lineQuantity, deductedQuantity);
      if (BigDecimalUtils.compare(lineQuantity, BigDecimal.ZERO) <= 0) {
        break;
      }
    }
    return totalCost;
  }

  private void validateStockAvailability(
      Map<Integer, List<StockItem>> stockItemsByProductId, List<SaleLine> items) {
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
      Map<Integer, List<StockItem>> stockItemsByProductId, SaleLine item) {
    BigDecimal totalStock = getTotalStockForProduct(stockItemsByProductId, item);
    return BigDecimalUtils.compare(totalStock, item.getQuantity()) < 0;
  }

  private String buildInsufficientStockMessage(
      Map<Integer, List<StockItem>> stockItemsByProductId, SaleLine item) {
    BigDecimal totalStock = getTotalStockForProduct(stockItemsByProductId, item);
    return String.format(
        "Vendor Product Variant id: %d, Wanted: %s, Have: %s",
        item.getVendorProductVariant().getId(), item.getQuantity(), totalStock);
  }

  private BigDecimal getTotalStockForProduct(
      Map<Integer, List<StockItem>> stockItemsByProductId, SaleLine item) {
    return stockItemsByProductId
        .get(item.getVendorProductVariant().getVendorProduct().getId())
        .stream()
        .map(StockItem::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimalUtils::add);
  }

  private BigDecimal getSalePriceFromStock(List<StockItem> productStock, Unit unit) {
    var productVendorId = productStock.get(0).getPurchaseOrderLine().getVendorProduct().getId();
    var productPrice =
        productPriceRepository
            .findByVendorProductIdAndUnitId(productVendorId, unit.getId())
            .orElseThrow();
    return productPrice.getSalePrice();
  }

  private Map<Integer, List<StockItem>> getStockItemsByVendorProductId(List<SaleLine> items) {
    var vendorProductIds =
        items.stream()
            .map(item -> item.getVendorProductVariant().getVendorProduct().getId())
            .toList();
    return stockItemRepository.findByVendorProductIds(vendorProductIds).stream()
        .collect(
            Collectors.groupingBy(item -> item.getPurchaseOrderLine().getVendorProduct().getId()));
  }
}
