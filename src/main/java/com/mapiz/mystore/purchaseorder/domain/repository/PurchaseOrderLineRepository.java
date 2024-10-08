package com.mapiz.mystore.purchaseorder.domain.repository;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import java.util.List;
import java.util.Optional;

public interface PurchaseOrderLineRepository {
  List<PurchaseOrderLine> saveAll(List<PurchaseOrderLine> lines);

  Optional<PurchaseOrderLine> findById(Integer purchaseOrderLineId);

  void save(PurchaseOrderLine line);
}
