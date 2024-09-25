package com.mapiz.mystore.vendor.domain.repository;

import com.mapiz.mystore.vendor.domain.ProductVendor;

import java.util.List;

public interface ProductVendorRepository {

    List<ProductVendor> findBySupplierId(Integer vendorId);

    List<ProductVendor> findBySupplierIdAndProductIdIn(Integer supplierId, List<Integer> productIds);

    void deleteAll(List<ProductVendor> bySupplierId);

    List<ProductVendor> saveAll(List<ProductVendor> list);
}
