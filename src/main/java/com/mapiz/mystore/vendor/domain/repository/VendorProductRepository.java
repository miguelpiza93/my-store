package com.mapiz.mystore.vendor.domain.repository;

import com.mapiz.mystore.vendor.domain.VendorProduct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VendorProductRepository {

  List<VendorProduct> findBySupplierId(Integer vendorId);

  List<VendorProduct> findBySupplierIdAndProductIdIn(Integer supplierId, List<Integer> productIds);

  Optional<VendorProduct> findByVendorIdAndProductId(Integer supplierId, Integer productId);

  void deleteAll(List<VendorProduct> bySupplierId);

  List<VendorProduct> saveAll(List<VendorProduct> list);

  List<VendorProduct> findAllById(Collection<Integer> ids);
}
