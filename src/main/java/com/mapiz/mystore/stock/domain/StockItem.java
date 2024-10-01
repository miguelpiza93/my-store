package com.mapiz.mystore.stock.domain;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockItem {
  private Integer id;
  private Product product;
  private int quantity;
  private double salePrice;

  public StockItem(PurchaseOrderLine purchaseOrderLine) {
    this.product = purchaseOrderLine.getProduct();
    this.quantity = purchaseOrderLine.getQuantity();
  }

  public void incrementQuantity(Integer quantity) {
    this.quantity += quantity;
  }
}
