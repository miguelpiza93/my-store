package com.mapiz.mystore.purchaseorder.domain.repository;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import java.util.List;

public interface PurchaseOrderLineRepository {
  List<PurchaseOrderLine> save(List<PurchaseOrderLine> lines);
}
