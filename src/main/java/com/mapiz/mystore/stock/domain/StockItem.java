package com.mapiz.mystore.stock.domain;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItem {
  private final Product product;
  private int quantity;
  private double salePrice;

  public StockItem(PurchaseOrderLine purchaseOrderLine) {
    this.product = purchaseOrderLine.getProduct();
    this.quantity = purchaseOrderLine.getQuantity();
  }
}
