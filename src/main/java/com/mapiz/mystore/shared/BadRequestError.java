package com.mapiz.mystore.shared;

public class BadRequestError extends RuntimeException {
  public BadRequestError(String message) {
    super(message);
  }
}
