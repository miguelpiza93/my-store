package com.mapiz.mystore.stock.domain;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockItem {
  private Integer id;
  private PurchaseOrderLine purchaseOrderLine;
  private BigDecimal quantity;
  private double salePrice;

  public StockItem(PurchaseOrderLine purchaseOrderLine) {
    this.purchaseOrderLine = purchaseOrderLine;
    this.quantity = BigDecimal.ZERO;
    this.incrementQuantity(purchaseOrderLine);
  }

  public void incrementQuantity(PurchaseOrderLine purchaseOrderLine) {
    var baseConversion = this.purchaseOrderLine.getProduct().getReferenceUnit().getBaseConversion();
    this.quantity =
        this.quantity.add(
            purchaseOrderLine.getQuantity().multiply(baseConversion.getConversionFactor()));
  }
}
