package com.mapiz.mystore.product.domain;

import com.mapiz.mystore.unit.domain.Conversion;
import com.mapiz.mystore.unit.domain.Unit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Product {
  private Integer id;
  private String name;
  private String description;
  private Unit referenceUnit;
  private List<ProductPrice> prices;

  public Optional<Unit> getDerivedUnit(int unitId) {
    if (referenceUnit.getId() == unitId) {
      return Optional.of(referenceUnit);
    }

    return referenceUnit.getUnitConversions().stream()
        .filter(conversion -> conversion.getToUnit().getId() == unitId)
        .map(Conversion::getToUnit)
        .findFirst();
  }

  public List<ProductPrice> getPrices() {
    if (prices == null || prices.isEmpty()) {
      initPrices();
    }

    return prices;
  }

  private void initPrices() {
    this.prices = new ArrayList<>();

    this.referenceUnit
        .getUnitConversions()
        .forEach(conversion -> addPriceForUnit(conversion.getToUnit()));

    addPriceForUnit(this.referenceUnit);
  }

  private void addPriceForUnit(Unit unit) {
    this.prices.add(
        ProductPrice.builder().unit(unit).product(this).salePrice(BigDecimal.ZERO).build());
  }
}
