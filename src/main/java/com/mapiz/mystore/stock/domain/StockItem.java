package com.mapiz.mystore.stock.domain;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.util.BigDecimalUtils;
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
    var baseQuantity =
        BigDecimalUtils.multiply(
            this.purchaseOrderLine.getQuantity(), baseConversion.getConversionFactor());
    this.quantity = BigDecimalUtils.add(this.quantity, baseQuantity);
  }

  public BigDecimal deductQuantity(BigDecimal quantityToSubtract) {
    BigDecimal min = BigDecimalUtils.min(this.quantity, quantityToSubtract);
    this.quantity = BigDecimalUtils.subtract(this.quantity, min);
    return min;
  }
}
