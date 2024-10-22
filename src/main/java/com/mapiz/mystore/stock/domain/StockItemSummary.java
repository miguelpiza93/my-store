package com.mapiz.mystore.stock.domain;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class StockItemSummary {

  private Product product;
  private List<StockItem> items;

  public StockItemSummary(StockItem stock) {
    this.items = new ArrayList<>();
    this.items.add(stock);
    this.product = stock.getPurchaseOrderLine().getVendorProduct().getProduct();
  }

  public void addStock(StockItem stock) {
    this.items.add(stock);
  }

  public BigDecimal getRawQuantity() {
    var purchaseOrderLines = getPurchaseOrderLines();
    return purchaseOrderLines.stream()
        .map(PurchaseOrderLine::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimalUtils::add);
  }

  public BigDecimal getWeightedCost() {
    var purchaseOrderLines = getPurchaseOrderLines();
    var totalCost = getTotalCost(purchaseOrderLines);

    var totalQuantity = this.getRawQuantity();
    var weightedCostInReferenceUnit = BigDecimalUtils.divide(totalCost, totalQuantity);
    var baseConversionFactor = product.getReferenceUnit().getBaseConversion().getConversionFactor();
    return BigDecimalUtils.divide(weightedCostInReferenceUnit, baseConversionFactor);
  }

  public BigDecimal getQuantity() {
    var referenceQuantity = this.getRawQuantity();
    var conversionFactor = product.getReferenceUnit().getBaseConversion().getConversionFactor();
    return BigDecimalUtils.multiply(referenceQuantity, conversionFactor);
  }

  private BigDecimal getTotalCost(List<PurchaseOrderLine> purchaseOrderLines) {
    return purchaseOrderLines.stream()
        .map(PurchaseOrderLine::getTotal)
        .reduce(BigDecimal.ZERO, BigDecimalUtils::add);
  }

  private List<PurchaseOrderLine> getPurchaseOrderLines() {
    return this.items.stream().map(StockItem::getPurchaseOrderLine).toList();
  }
}
