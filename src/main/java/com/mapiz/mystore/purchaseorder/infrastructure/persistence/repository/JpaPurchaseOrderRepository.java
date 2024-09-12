package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository;

import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Integer>{
}
