package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.command.RegisterSaleCommand;
import com.mapiz.mystore.sales.application.command.RegisterSaleCommandItem;
import com.mapiz.mystore.sales.application.exception.NotEnoughStockException;
import com.mapiz.mystore.sales.application.usecase.RegisterSaleUseCase;
import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.domain.SaleLine;
import com.mapiz.mystore.sales.domain.repository.SaleLineRepository;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.domain.repository.StockItemRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.domain.repository.VendorProductRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterSaleUseCaseImpl implements RegisterSaleUseCase {

  private final SaleRepository saleRepository;
  private final SaleLineRepository saleLineRepository;
  private final StockItemRepository stockItemRepository;
  private final VendorProductRepository vendorProductRepository;

  @Override
  public Sale apply(RegisterSaleCommand command) {
    var savedSale = saveSale();
    var vendorProductIds = getProductIds(command);
    Map<Integer, List<StockItem>> availableStockItems = getAvailableStockItemsMap(vendorProductIds);
    var savedLines =
        saveLines(
            buildSaleLines(
                getVendorProducts(vendorProductIds), availableStockItems, command, savedSale));
    updateStockItems(availableStockItems);
    savedSale.setLines(savedLines);
    return savedSale;
  }

  private void updateStockItems(Map<Integer, List<StockItem>> availableStockItems) {
    stockItemRepository.saveAll(
        availableStockItems.values().stream().flatMap(List::stream).toList());
  }

  private List<SaleLine> buildSaleLines(
      List<VendorProduct> vendorProducts,
      Map<Integer, List<StockItem>> availableStockItems,
      RegisterSaleCommand command,
      Sale savedSale) {
    Map<Integer, VendorProduct> vendorProductsMap =
        vendorProducts.stream().collect(Collectors.toMap(VendorProduct::getId, p -> p));
    return command.getItems().stream()
        .map(
            item ->
                buildSaleLine(
                    savedSale,
                    item,
                    vendorProductsMap.get(item.getVendorProductId()),
                    availableStockItems.get(item.getVendorProductId())))
        .toList();
  }

  private Map<Integer, List<StockItem>> getAvailableStockItemsMap(Set<Integer> vendorProductIds) {
    var items = getAvailableStockItems(vendorProductIds);
    return items.stream()
        .collect(
            Collectors.groupingBy(
                stockItem -> stockItem.getPurchaseOrderLine().getVendorProduct().getId(),
                Collectors.toList()));
  }

  private List<StockItem> getAvailableStockItems(Set<Integer> vendorProductIds) {
    return stockItemRepository.findByVendorProductIds(vendorProductIds);
  }

  private List<VendorProduct> getVendorProducts(Set<Integer> vendorProductIds) {
    return vendorProductRepository.findAllById(vendorProductIds);
  }

  private Set<Integer> getProductIds(RegisterSaleCommand command) {
    return command.getItems().stream()
        .map(RegisterSaleCommandItem::getVendorProductId)
        .collect(Collectors.toSet());
  }

  private SaleLine buildSaleLine(
      Sale savedSale,
      RegisterSaleCommandItem item,
      VendorProduct vendorProduct,
      List<StockItem> stockItems) {
    var vendorProductVariant =
        vendorProduct
            .getVariant(item.getUnitId())
            .orElseThrow(() -> new RuntimeException("Variant not found"));
    BigDecimal cost = deductQuantity(stockItems, vendorProductVariant, item.getQuantity());
    return SaleLine.builder()
        .sale(savedSale)
        .vendorProductVariant(vendorProductVariant)
        .quantity(item.getQuantity())
        .unitPrice(vendorProductVariant.getSalePrice())
        .total(BigDecimalUtils.multiply(item.getQuantity(), vendorProductVariant.getSalePrice()))
        .cost(cost)
        .build();
  }

  private BigDecimal deductQuantity(
      List<StockItem> stockItem,
      VendorProductUnitVariant vendorProductVariant,
      BigDecimal quantity) {
    var availableQuantityInBaseUnit =
        stockItem.stream()
            .map(StockItem::getQuantity)
            .reduce(BigDecimal.ZERO, BigDecimalUtils::add);
    var wantedQuantityInBaseUnit =
        BigDecimalUtils.multiply(
            quantity, vendorProductVariant.getUnit().getBaseConversion().getConversionFactor());
    if (BigDecimalUtils.compare(wantedQuantityInBaseUnit, availableQuantityInBaseUnit) > 0) {
      throw new NotEnoughStockException(
          String.format(
              "Vendor Product id: %s, Wanted: %s, Have: %s",
              vendorProductVariant.getVendorProduct().getId(),
              wantedQuantityInBaseUnit,
              availableQuantityInBaseUnit));
    }
    BigDecimal cost = BigDecimal.ZERO;
    var quantityToDeduct = wantedQuantityInBaseUnit;
    for (StockItem item : stockItem) {
      var deducted = item.deductQuantity(quantityToDeduct);
      quantityToDeduct = BigDecimalUtils.subtract(quantityToDeduct, deducted);
      cost =
          BigDecimalUtils.add(
              cost,
              BigDecimalUtils.multiply(deducted, item.getPurchaseOrderLine().getCostPerBaseUnit()));
      if (BigDecimalUtils.compare(quantityToDeduct, BigDecimal.ZERO) == 0) {
        break;
      }
    }
    return cost;
  }

  private Sale saveSale() {
    return saleRepository.save(Sale.builder().createdAt(Instant.now()).status("CLOSED").build());
  }

  private List<SaleLine> saveLines(List<SaleLine> saleLines) {
    return saleLineRepository.saveAll(saleLines);
  }
}
