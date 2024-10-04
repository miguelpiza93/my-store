package com.mapiz.mystore.vendor.infrastructure.persistence.repository;

import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductVendorRepository extends JpaRepository<VendorProductEntity, Integer> {

  List<VendorProductEntity> findByVendorId(Integer vendorId);

  List<VendorProductEntity> findByVendorIdAndProductIdIn(
      Integer vendorId, List<Integer> productIds);
}
