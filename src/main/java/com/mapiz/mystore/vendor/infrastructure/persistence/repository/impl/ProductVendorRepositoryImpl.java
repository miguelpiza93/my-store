package com.mapiz.mystore.vendor.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductMapper;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaProductVendorRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductVendorRepositoryImpl implements ProductVendorRepository {

  private final JpaProductVendorRepository jpaRepository;

  @Override
  public List<VendorProduct> findBySupplierId(Integer vendorId) {
    return jpaRepository.findByVendorId(vendorId).stream().map(getMapper()).toList();
  }

  @Override
  public List<VendorProduct> findBySupplierIdAndProductIdIn(
      Integer supplierId, List<Integer> productIds) {
    return jpaRepository.findByVendorIdAndProductIdIn(supplierId, productIds).stream()
        .map(getMapper())
        .toList();
  }

  @Override
  public Optional<VendorProduct> findBySupplierIdAndProductId(
      Integer supplierId, Integer productId) {
    return jpaRepository.findByVendorIdAndProductId(supplierId, productId).map(getMapper());
  }

  @Override
  public void deleteAll(List<VendorProduct> bySupplierId) {
    jpaRepository.deleteAllById(bySupplierId.stream().map(VendorProduct::getId).toList());
  }

  @Override
  public List<VendorProduct> saveAll(List<VendorProduct> list) {
    var toSave = list.stream().map(VendorProductMapper.INSTANCE::modelToEntity).toList();
    return jpaRepository.saveAll(toSave).stream().map(getMapper()).toList();
  }

  private Function<VendorProductEntity, VendorProduct> getMapper() {
    return productVendor ->
        VendorProductMapper.INSTANCE.entityToModel(
            productVendor, new CycleAvoidingMappingContext());
  }
}
