package com.mapiz.mystore.stock.domain;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockItem {
  private Integer id;
  private PurchaseOrderLine purchaseOrderLine;
  private double quantity;
  private double salePrice;

  public StockItem(PurchaseOrderLine purchaseOrderLine) {
    this.purchaseOrderLine = purchaseOrderLine;
    this.incrementQuantity(purchaseOrderLine);
  }

  public void incrementQuantity(PurchaseOrderLine purchaseOrderLine) {
    var baseConversion = this.purchaseOrderLine.getProduct().getReferenceUnit().getBaseConversion();
    this.quantity += purchaseOrderLine.getQuantity() * baseConversion.getConversionFactor();
  }
}
