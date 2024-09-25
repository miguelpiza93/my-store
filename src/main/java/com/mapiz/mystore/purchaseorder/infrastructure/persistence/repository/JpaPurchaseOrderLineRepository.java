package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository;

import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLineEntity, Integer>{
}
