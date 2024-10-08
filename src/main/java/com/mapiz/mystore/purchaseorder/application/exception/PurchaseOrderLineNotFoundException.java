package com.mapiz.mystore.purchaseorder.application.exception;

import com.mapiz.mystore.shared.ResourceNotFound;

public class PurchaseOrderLineNotFoundException extends ResourceNotFound {
  public PurchaseOrderLineNotFoundException(Integer id) {
    super("Purchase order line with id %d not found".formatted(id));
  }
}
