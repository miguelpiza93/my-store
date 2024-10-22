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
  private List<StockItem> items = new ArrayList<>();

  public StockItemSummary(Product product) {
    this.product = product;
  }

  public void addStock(StockItem stock) {
    this.items.add(stock);
  }

  public BigDecimal getRawQuantity() {
    return items.stream()
        .map(StockItem::getPurchaseOrderLine)
        .map(PurchaseOrderLine::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimalUtils::add);
  }

  public BigDecimal getWeightedCost() {
    List<PurchaseOrderLine> purchaseOrderLines = getPurchaseOrderLines();
    BigDecimal totalCost = getTotalCost(purchaseOrderLines);
    BigDecimal totalQuantity = this.getRawQuantity();

    if (totalQuantity.equals(BigDecimal.ZERO)) {
      return BigDecimal.ZERO;
    }

    BigDecimal weightedCostInReferenceUnit = BigDecimalUtils.divide(totalCost, totalQuantity);
    BigDecimal baseConversionFactor =
        product.getReferenceUnit().getBaseConversion().getConversionFactor();

    return BigDecimalUtils.divide(weightedCostInReferenceUnit, baseConversionFactor);
  }

  public BigDecimal getQuantity() {
    BigDecimal referenceQuantity = this.getRawQuantity();
    BigDecimal conversionFactor =
        product.getReferenceUnit().getBaseConversion().getConversionFactor();

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
