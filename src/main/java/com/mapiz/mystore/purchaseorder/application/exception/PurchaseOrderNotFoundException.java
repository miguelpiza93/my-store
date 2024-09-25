package com.mapiz.mystore.purchaseorder.application.exception;


import com.mapiz.mystore.shared.ResourceNotFound;

public class PurchaseOrderNotFoundException extends ResourceNotFound {
    public PurchaseOrderNotFoundException(String message) {
        super(message);
    }
}
