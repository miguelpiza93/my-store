package com.mapiz.mystore.sales.domain;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Sale {

  private Integer id;

  private Product product;

  private BigDecimal quantity;

  private Unit unit;

  private BigDecimal price;

  private BigDecimal total;

  private BigDecimal cost;

  private Instant createdAt;

  public void setPrice(BigDecimal price) {
    this.price = price;
    this.calculateTotal();
  }

  private void calculateTotal() {
    var baseQuantity =
        BigDecimalUtils.multiply(quantity, this.unit.getBaseConversion().getConversionFactor());
    this.total = BigDecimalUtils.multiply(baseQuantity, price);
  }

  public BigDecimal getBaseQuantity() {
    return BigDecimalUtils.multiply(quantity, this.unit.getBaseConversion().getConversionFactor());
  }
}
