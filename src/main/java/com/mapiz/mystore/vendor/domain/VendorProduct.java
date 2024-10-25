package com.mapiz.mystore.vendor.domain;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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
        .filter(salePrices -> !salePrices.isEmpty())
        .map(this::completeSalePrices)
        .orElseGet(this::getInitialSalePricesList);
  }

  private List<VendorProductUnitVariant> completeSalePrices(
      List<VendorProductUnitVariant> configuredPrices) {
    var allExpectedPrices = getInitialSalePricesList();
    return allExpectedPrices.stream()
        .map(initialPrice -> getVariant(configuredPrices, initialPrice))
        .toList();
  }

  private static VendorProductUnitVariant getVariant(
      List<VendorProductUnitVariant> configuredPrices, VendorProductUnitVariant initialPrice) {
    return configuredPrices.stream()
        .filter(price -> price.getUnit().getId().equals(initialPrice.getUnit().getId()))
        .findFirst()
        .orElse(initialPrice);
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
        + "Prices"
        + salePrices
        + '}';
  }
}
