package com.mapiz.mystore.vendor.domain.repository;

import com.mapiz.mystore.vendor.domain.VendorProduct;
import java.util.List;

public interface ProductVendorRepository {

  List<VendorProduct> findBySupplierId(Integer vendorId);

  List<VendorProduct> findBySupplierIdAndProductIdIn(Integer supplierId, List<Integer> productIds);

  void deleteAll(List<VendorProduct> bySupplierId);

  List<VendorProduct> saveAll(List<VendorProduct> list);
}
