package com.mapiz.mystore.stock.infrastructure.persistence.repository;

import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductUnitVariantEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUnitStockItemRepository
    extends JpaRepository<VendorProductUnitVariantEntity, Integer> {
  Optional<VendorProductUnitVariantEntity> findByVendorProductIdAndUnitId(
      Integer vendorProductId, Integer unitId);
}
