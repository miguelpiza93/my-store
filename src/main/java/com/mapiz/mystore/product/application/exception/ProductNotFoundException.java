package com.mapiz.mystore.product.application.exception;

import com.mapiz.mystore.shared.ResourceNotFound;

public class ProductNotFoundException extends ResourceNotFound {
  public ProductNotFoundException(int id) {
    super(String.format("Product with id %d not found", id));
  }
}
