package com.mapiz.mystore.vendor.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.ProductVendorMapper;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaProductVendorRepository;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductVendorRepositoryImpl implements ProductVendorRepository {

  private final JpaProductVendorRepository jpaRepository;

  @Override
  public List<ProductVendor> findBySupplierId(Integer vendorId) {
    return jpaRepository.findByVendorId(vendorId).stream().map(getMapper()).toList();
  }

  @Override
  public List<ProductVendor> findBySupplierIdAndProductIdIn(
      Integer supplierId, List<Integer> productIds) {
    return jpaRepository.findByVendorIdAndProductIdIn(supplierId, productIds).stream()
        .map(getMapper())
        .toList();
  }

  @Override
  public void deleteAll(List<ProductVendor> bySupplierId) {
    jpaRepository.deleteAllById(bySupplierId.stream().map(ProductVendor::getId).toList());
  }

  @Override
  public List<ProductVendor> saveAll(List<ProductVendor> list) {
    var toSave = list.stream().map(ProductVendorMapper.INSTANCE::modelToEntity).toList();
    return jpaRepository.saveAll(toSave).stream().map(getMapper()).toList();
  }

  private Function<VendorProductEntity, ProductVendor> getMapper() {
    return productVendor ->
        ProductVendorMapper.INSTANCE.entityToModel(
            productVendor, new CycleAvoidingMappingContext());
  }
}
