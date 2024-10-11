package com.mapiz.mystore.purchaseorder.domain;

import com.mapiz.mystore.product.domain.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderLine {
  private Integer id;
  private Instant createdAt;
  private Product product;
  private BigDecimal quantity;
  private BigDecimal unitPrice;
  private PurchaseOrder purchaseOrder;

  public BigDecimal getCostPerBaseUnit() {
    var baseConversion = product.getReferenceUnit().getBaseConversion();
    var conversionFactor = baseConversion.getConversionFactor();
    return this.unitPrice.divide(conversionFactor, RoundingMode.HALF_UP);
  }

  @Override
  public String toString() {
    return "PurchaseOrderLine{"
        + "id="
        + id
        + ", product="
        + product
        + ", quantity="
        + quantity
        + ", unitPrice="
        + unitPrice
        + ", purchaseOrder="
        + purchaseOrder.getId()
        + '}';
  }
}
