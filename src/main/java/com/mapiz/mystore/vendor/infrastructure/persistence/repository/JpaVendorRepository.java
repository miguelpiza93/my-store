package com.mapiz.mystore.vendor.infrastructure.persistence.repository;

import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaVendorRepository extends JpaRepository<VendorEntity, Integer> {}
