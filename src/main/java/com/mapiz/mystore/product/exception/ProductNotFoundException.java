package com.mapiz.mystore.product.exception;

import com.mapiz.mystore.shared.ResourceNotFound;

public class ProductNotFoundException extends ResourceNotFound {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
