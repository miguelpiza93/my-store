package com.mapiz.mystore.purchaseorder.application.usecase;

import com.mapiz.mystore.purchaseorder.application.command.CreatePurchaseOrderCommand;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;

import java.util.function.Function;

@FunctionalInterface
public interface CreatePurchaseOrderUseCase extends Function<CreatePurchaseOrderCommand, PurchaseOrder> {
}
