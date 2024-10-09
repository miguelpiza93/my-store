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

  public BigDecimal deductQuantity(BigDecimal quantityToSubtract) {
    BigDecimal min = quantityToSubtract.min(this.quantity);
    this.quantity = this.quantity.subtract(min);
    return min;
  }
}
