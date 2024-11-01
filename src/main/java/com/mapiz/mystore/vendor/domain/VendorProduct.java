package com.mapiz.mystore.vendor.domain;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorProduct {
  private Integer id;
  private Vendor vendor;
  private Product product;
  private BigDecimal price;
  private List<VendorProductUnitVariant> salePrices;

  public List<VendorProductUnitVariant> getSalePrices() {
    return Optional.ofNullable(salePrices)
        .map(this::completeWithDefaults)
        .orElseGet(this::getInitialSalePricesList);
  }

  public Optional<VendorProductUnitVariant> getVariant(int unitId) {
    return getSalePrices().stream()
        .filter(variant -> variant.getUnit().getId() == unitId)
        .findFirst();
  }

  private List<VendorProductUnitVariant> completeWithDefaults(
      List<VendorProductUnitVariant> salePrices) {
    var configuredPricesMap =
        salePrices.stream()
            .collect(Collectors.toMap(variant -> variant.getUnit().getId(), variant -> variant));

    return getInitialSalePricesList().stream()
        .map(
            initialPrice ->
                configuredPricesMap.getOrDefault(initialPrice.getUnit().getId(), initialPrice))
        .toList();
  }

  private List<VendorProductUnitVariant> getInitialSalePricesList() {
    return product.getAllUnits().stream().map(this::initProductVariant).toList();
  }

  private VendorProductUnitVariant initProductVariant(Unit unit) {
    return VendorProductUnitVariant.builder()
        .unit(unit)
        .salePrice(BigDecimalUtils.valueOf(BigDecimal.ZERO))
        .build();
  }

  @Override
  public String toString() {
    return "VendorProduct{"
        + "id="
        + id
        + ", vendor="
        + vendor
        + ", product="
        + product
        + ", price="
        + price
        + ", salePricesCount="
        + (salePrices == null ? 0 : salePrices.size())
        + '}';
  }
}
