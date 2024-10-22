package com.mapiz.mystore.product.domain;

import com.mapiz.mystore.unit.domain.Conversion;
import com.mapiz.mystore.unit.domain.Unit;
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

  public Optional<Unit> getDerivedUnit(int unitId) {
    if (referenceUnit.getId() == unitId) {
      return Optional.of(referenceUnit);
    }

    return referenceUnit.getUnitConversions().stream()
        .filter(conversion -> conversion.getToUnit().getId() == unitId)
        .map(Conversion::getToUnit)
        .findFirst();
  }
}
