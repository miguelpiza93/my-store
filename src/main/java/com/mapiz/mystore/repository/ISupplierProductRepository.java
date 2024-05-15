package com.mapiz.mystore.repository;

import com.mapiz.mystore.entity.SupplierProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierProductRepository extends CrudRepository<SupplierProduct, Integer> {
}
