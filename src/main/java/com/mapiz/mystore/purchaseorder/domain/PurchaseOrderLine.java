package com.mapiz.mystore.purchaseorder.domain;

import com.mapiz.mystore.util.BigDecimalUtils;
import com.mapiz.mystore.vendor.domain.VendorProduct;
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
  private VendorProduct vendorProduct;
  private BigDecimal quantity;
  private BigDecimal unitPrice;
  private BigDecimal total;
  private PurchaseOrder purchaseOrder;

  public BigDecimal getTotal() {
    return BigDecimalUtils.multiply(unitPrice, quantity);
  }

  public BigDecimal getCostPerBaseUnit() {
    var baseConversion = vendorProduct.getProduct().getReferenceUnit().getBaseConversion();
    var conversionFactor = baseConversion.getConversionFactor();
    return this.unitPrice.divide(conversionFactor, RoundingMode.HALF_UP);
  }

  @Override
  public String toString() {
    return "PurchaseOrderLine{"
        + "id="
        + id
        + ", vendorProduct="
        + vendorProduct
        + ", quantity="
        + quantity
        + ", unitPrice="
        + unitPrice
        + ", purchaseOrder="
        + purchaseOrder.getId()
        + '}';
  }
}
