package com.mapiz.mystore.stock.application.exception;

import com.mapiz.mystore.shared.BadRequestError;

public class PurchaseOrderWasAlreadyReceivedException extends BadRequestError {
  public PurchaseOrderWasAlreadyReceivedException(Integer id) {
    super(String.format("Purchase order with id %s was already received", id));
  }
}
