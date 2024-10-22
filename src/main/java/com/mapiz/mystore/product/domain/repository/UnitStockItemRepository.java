package com.mapiz.mystore.product.domain.repository;

import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import java.util.Optional;

public interface UnitStockItemRepository {

  VendorProductUnitVariant save(VendorProductUnitVariant productPrice);

  Optional<VendorProductUnitVariant> findByVendorProductIdAndUnitId(
      Integer productVendorId, Integer unitId);
}
