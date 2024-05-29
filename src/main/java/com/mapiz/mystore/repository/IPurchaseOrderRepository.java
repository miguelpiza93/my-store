package com.mapiz.mystore.repository;

import com.mapiz.mystore.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer>{
}
