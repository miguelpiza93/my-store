package com.mapiz.mystore.repository;

import com.mapiz.mystore.entity.PurchaseOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLine, Integer>{
}
