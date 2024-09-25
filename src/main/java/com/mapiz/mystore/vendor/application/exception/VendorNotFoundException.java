package com.mapiz.mystore.vendor.application.exception;

import com.mapiz.mystore.shared.ResourceNotFound;

public class VendorNotFoundException extends ResourceNotFound {
    public VendorNotFoundException(String message) {
        super(message);
    }
}
