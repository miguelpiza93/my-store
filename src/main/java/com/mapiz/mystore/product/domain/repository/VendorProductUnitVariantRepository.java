package com.mapiz.mystore.product.domain.repository;

import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import java.util.Optional;

public interface VendorProductUnitVariantRepository {

  void save(VendorProductUnitVariant productPrice);

  Optional<VendorProductUnitVariant> findByVendorProductIdAndUnitId(
      Integer productVendorId, Integer unitId);
}
