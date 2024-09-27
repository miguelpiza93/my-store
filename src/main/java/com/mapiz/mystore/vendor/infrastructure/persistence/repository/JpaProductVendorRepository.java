package com.mapiz.mystore.vendor.infrastructure.persistence.repository;

import com.mapiz.mystore.vendor.infrastructure.persistence.entity.ProductVendorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductVendorRepository extends JpaRepository<ProductVendorEntity, Integer> {

  List<ProductVendorEntity> findByVendorId(Integer vendorId);

  List<ProductVendorEntity> findByVendorIdAndProductIdIn(
      Integer vendorId, List<Integer> productIds);
}
