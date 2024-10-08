package com.mapiz.mystore.purchaseorder.application.usecase;

import com.mapiz.mystore.purchaseorder.application.command.UpdateUnitPricePurchaseOrderLineCommand;
import java.util.function.Consumer;

@FunctionalInterface
public interface UpdateUnitPricePurchaseOrderLineUseCase
    extends Consumer<UpdateUnitPricePurchaseOrderLineCommand> {}
