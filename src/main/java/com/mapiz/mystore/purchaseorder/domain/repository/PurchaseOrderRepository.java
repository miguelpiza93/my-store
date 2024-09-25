package com.mapiz.mystore.purchaseorder.domain.repository;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepository {
    PurchaseOrder save(PurchaseOrder purchaseOrder);

    Optional<PurchaseOrder> findById(Integer integer);

    List<PurchaseOrder> findAll();
}
