package com.mapiz.mystore.shared;

public class ResourceNotFound extends RuntimeException {
  public ResourceNotFound(String message) {
    super(message);
  }
}
