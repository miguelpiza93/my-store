package com.mapiz.mystore.vendor.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.domain.repository.VendorProductRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductMapper;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductUnitVariantMapper;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaProductVendorRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductVendorRepositoryImpl implements VendorProductRepository {

  private final VendorProductMapper vendorProductMapper;
  private final VendorProductUnitVariantMapper vendorProductUnitVariantMapper;
  private final JpaProductVendorRepository jpaRepository;

  @Override
  public List<VendorProduct> findBySupplierId(Integer vendorId) {
    return jpaRepository.findByVendorId(vendorId).stream()
        .map(vendorProductMapper::entityToModel)
        .toList();
  }

  @Override
  public List<VendorProduct> findBySupplierIdAndProductIdIn(
      Integer supplierId, List<Integer> productIds) {
    return jpaRepository.findByVendorIdAndProductIdIn(supplierId, productIds).stream()
        .map(vendorProductMapper::entityToModel)
        .toList();
  }

  @Override
  public Optional<VendorProduct> findByVendorIdAndProductId(Integer supplierId, Integer productId) {
    return jpaRepository
        .findByVendorIdAndProductId(supplierId, productId)
        .map(
            entity -> {
              VendorProduct vendorProduct = vendorProductMapper.entityToModel(entity);
              List<VendorProductUnitVariant> salePrices =
                  entity.getSalePrices() != null
                      ? entity.getSalePrices().stream()
                          .map(vendorProductUnitVariantMapper::toDomain)
                          .toList()
                      : Collections.emptyList();
              vendorProduct.setSalePrices(salePrices);
              return vendorProduct;
            });
  }

  @Override
  public void deleteAll(List<VendorProduct> bySupplierId) {
    jpaRepository.deleteAllById(bySupplierId.stream().map(VendorProduct::getId).toList());
  }

  @Override
  public List<VendorProduct> saveAll(List<VendorProduct> list) {
    var toSave = list.stream().map(vendorProductMapper::modelToEntity).toList();
    return jpaRepository.saveAll(toSave).stream().map(vendorProductMapper::entityToModel).toList();
  }

  @Override
  public List<VendorProduct> findAllById(Collection<Integer> ids) {
    return jpaRepository.findAllById(ids).stream().map(vendorProductMapper::entityToModel).toList();
  }
}
