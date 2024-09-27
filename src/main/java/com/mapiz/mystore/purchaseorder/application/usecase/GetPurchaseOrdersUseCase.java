package com.mapiz.mystore.purchaseorder.application.usecase;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetPurchaseOrdersUseCase extends Supplier<List<PurchaseOrder>> {}
