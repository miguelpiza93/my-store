package com.mapiz.mystore.vendor.domain;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class VendorProduct {
  private Integer id;
  private Vendor vendor;
  private Product product;
  private BigDecimal price;
  private List<VendorProductUnitVariant> salePrices;

  public List<VendorProductUnitVariant> getSalePrices() {
    initPrices();
    return salePrices;
  }

  private void initPrices() {
    this.product
        .getReferenceUnit()
        .getUnitConversions()
        .forEach(conversion -> addPriceForUnit(conversion.getToUnit()));
    addPriceForUnit(this.product.getReferenceUnit());
  }

  private void addPriceForUnit(Unit unit) {
    var isConfigured =
        this.salePrices.stream().anyMatch(price -> price.getUnit().getId().equals(unit.getId()));
    if (isConfigured) {
      return;
    }
    this.salePrices.add(
        VendorProductUnitVariant.builder()
            .unit(unit)
            .salePrice(BigDecimalUtils.valueOf(BigDecimal.ZERO))
            .build());
  }
}
