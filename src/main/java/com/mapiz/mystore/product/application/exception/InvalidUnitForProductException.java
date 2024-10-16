package com.mapiz.mystore.product.application.exception;

import com.mapiz.mystore.shared.BadRequestError;

public class InvalidUnitForProductException extends BadRequestError {
  public InvalidUnitForProductException(int unitId, int productId) {
    super(String.format("The unit %d is not valid for product %d", unitId, productId));
  }
}
