package com.mapiz.mystore.vendor.domain.repository;

import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.ProductVendorEntity;

import java.util.List;

public interface ProductVendorRepository {

    List<ProductVendorEntity> findBySupplierId(Integer vendorId);

    List<ProductVendorEntity> findBySupplierIdAndProductIdIn(Integer supplierId, List<Integer> productIds);

    void deleteAll(List<ProductVendorEntity> bySupplierId);

    List<ProductVendor> saveAll(List<ProductVendor> list);
}
