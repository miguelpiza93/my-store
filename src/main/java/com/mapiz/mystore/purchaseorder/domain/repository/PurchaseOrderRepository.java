package com.mapiz.mystore.purchaseorder.domain.repository;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;

public interface PurchaseOrderRepository {
    PurchaseOrder save(PurchaseOrder purchaseOrder);
}
