package com.mapiz.mystore.product.domain;

import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.UnitConversion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        .map(UnitConversion::getToUnit)
        .findFirst();
  }

  public List<Unit> getAllUnits() {
    var result =
        referenceUnit.getUnitConversions().stream()
            .map(UnitConversion::getToUnit)
            .collect(
                Collectors.toCollection(
                    () -> new ArrayList<>(referenceUnit.getUnitConversions().size() + 1)));

    result.add(referenceUnit);
    return result;
  }

  @Override
  public String toString() {
    return "Product{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", referenceUnit="
        + referenceUnit.getName()
        + '}';
  }
}
