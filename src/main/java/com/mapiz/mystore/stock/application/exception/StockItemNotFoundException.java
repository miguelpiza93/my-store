package com.mapiz.mystore.stock.application.exception;

import com.mapiz.mystore.shared.ResourceNotFound;

public class StockItemNotFoundException extends ResourceNotFound {
  public StockItemNotFoundException(int id) {
    super(String.format("Stock item for product id %d not found", id));
  }
}
