package com.mapiz.mystore.vendor.domain.repository;

import com.mapiz.mystore.vendor.domain.Vendor;

import java.util.List;
import java.util.Optional;

public interface VendorRepository {
    Optional<Vendor> findById(Integer supplierId);

    Vendor save(Vendor vendor);

    void deleteById(Integer integer);

    List<Vendor> findAll();
}
