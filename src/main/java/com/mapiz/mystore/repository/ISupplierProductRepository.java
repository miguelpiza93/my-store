package com.mapiz.mystore.repository;

import com.mapiz.mystore.entity.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {

    List<SupplierProduct> findBySupplierId(Integer supplierId);
}
