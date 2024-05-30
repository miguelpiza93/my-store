package com.mapiz.mystore.repository;

import com.mapiz.mystore.entity.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {

    List<SupplierProduct> findBySupplierId(Integer supplierId);
    Optional<SupplierProduct> findBySupplierIdAndProductId(Integer supplierId, Integer productId);
}
