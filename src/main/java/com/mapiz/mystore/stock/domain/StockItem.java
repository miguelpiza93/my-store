package com.mapiz.mystore.stock.domain;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItem {
  private final Integer productId;
  private final String name;
  private int quantity;
  private double salePrice;

  public StockItem(PurchaseOrderLine purchaseOrderLine) {
    this.productId = purchaseOrderLine.getProduct().getId();
    this.name = purchaseOrderLine.getProduct().getName();
    this.quantity = purchaseOrderLine.getQuantity();
  }
}
