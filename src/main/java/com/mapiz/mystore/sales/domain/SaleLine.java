package com.mapiz.mystore.sales.domain;

import com.mapiz.mystore.util.BigDecimalUtils;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SaleLine {

  private Integer id;

  private Sale sale;

  private VendorProductUnitVariant vendorProductVariant;

  private BigDecimal quantity;

  private BigDecimal unitPrice;

  private BigDecimal total;

  private BigDecimal cost;

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    this.calculateTotal();
  }

  private void calculateTotal() {
    this.total = BigDecimalUtils.multiply(quantity, unitPrice);
  }

  public BigDecimal getBaseQuantity() {
    return BigDecimalUtils.multiply(
        quantity, this.vendorProductVariant.getUnit().getBaseConversion().getConversionFactor());
  }
}
