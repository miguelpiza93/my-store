package com.mapiz.mystore.sales.application.exception;

import com.mapiz.mystore.shared.BadRequestError;

public class NotEnoughStockException extends BadRequestError {
  public NotEnoughStockException(Integer productId, Integer requested, Integer available) {
    super(
        String.format(
            "Not enough stock for product: %d. Requested: %d, Available: %d",
            productId, requested, available));
  }

  public NotEnoughStockException(String message) {
    super(message);
  }
}
