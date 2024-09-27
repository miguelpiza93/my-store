package com.mapiz.mystore.purchaseorder.application.exception;

import com.mapiz.mystore.shared.ResourceNotFound;

public class PurchaseOrderNotFoundException extends ResourceNotFound {
  public PurchaseOrderNotFoundException(Integer id) {
    super("Purchase order with id %d not found".formatted(id));
  }
}
