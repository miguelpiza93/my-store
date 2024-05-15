package com.mapiz.mystore.repository;

import com.mapiz.mystore.entity.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierRepository extends CrudRepository<Supplier, Integer> {
}
