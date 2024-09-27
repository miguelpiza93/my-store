package com.mapiz.mystore.product.application.exception;

import com.mapiz.mystore.shared.ResourceNotFound;

public class ProductNotFoundException extends ResourceNotFound {
  public ProductNotFoundException(String message) {
    super(message);
  }
}
