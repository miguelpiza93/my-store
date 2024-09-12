package com.mapiz.mystore.vendor.infrastructure.persistence.repository;

import com.mapiz.mystore.vendor.infrastructure.persistence.entity.ProductVendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProductVendorRepository extends JpaRepository<ProductVendorEntity, Integer> {

    List<ProductVendorEntity> findByVendorId(Integer vendorId);
    List<ProductVendorEntity> findByVendorIdAndProductIdIn(Integer vendorId, List<Integer> productIds);
}
