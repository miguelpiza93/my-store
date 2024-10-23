package com.mapiz.mystore.sales.domain;

import com.mapiz.mystore.util.BigDecimalUtils;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Sale {

  private Integer id;

  private VendorProductUnitVariant vendorProductVariant;

  private BigDecimal quantity;

  private BigDecimal price;

  private BigDecimal total;

  private BigDecimal cost;

  private Instant createdAt;

  public void setPrice(BigDecimal price) {
    this.price = price;
    this.calculateTotal();
  }

  private void calculateTotal() {
    this.total = BigDecimalUtils.multiply(quantity, price);
  }

  public BigDecimal getBaseQuantity() {
    return BigDecimalUtils.multiply(
        quantity, this.vendorProductVariant.getUnit().getBaseConversion().getConversionFactor());
  }
}
