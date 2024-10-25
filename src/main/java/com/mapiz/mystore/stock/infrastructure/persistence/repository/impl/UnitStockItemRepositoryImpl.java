package com.mapiz.mystore.stock.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.product.domain.repository.UnitStockItemRepository;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaUnitStockItemRepository;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductUnitVariantEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductUnitVariantMapper;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnitStockItemRepositoryImpl implements UnitStockItemRepository {

  private final VendorProductUnitVariantMapper vendorProductUnitVariantInfraMapper;
  private final JpaUnitStockItemRepository jpaProductPriceRepository;

  @Override
  public VendorProductUnitVariant save(VendorProductUnitVariant productPrice) {
    var productPriceEntity = vendorProductUnitVariantInfraMapper.toEntity(productPrice);
    return vendorProductUnitVariantInfraMapper.toDomain(
        jpaProductPriceRepository.save(productPriceEntity));
  }

  @Override
  public Optional<VendorProductUnitVariant> findByVendorProductIdAndUnitId(
      Integer productId, Integer unitId) {
    return jpaProductPriceRepository
        .findByVendorProductIdAndUnitId(productId, unitId)
        .map(getMapper());
  }

  private Function<VendorProductUnitVariantEntity, VendorProductUnitVariant> getMapper() {
    return vendorProductUnitVariantInfraMapper::toDomain;
  }
}
