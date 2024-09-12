package com.mapiz.mystore.vendor.application.exception;



public class VendorNotFoundException extends RuntimeException {
    public VendorNotFoundException(String message) {
        super(message);
    }
}
