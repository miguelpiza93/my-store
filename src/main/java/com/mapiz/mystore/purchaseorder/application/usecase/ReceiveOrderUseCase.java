package com.mapiz.mystore.purchaseorder.application.usecase;

import java.util.function.Consumer;

@FunctionalInterface
public interface ReceiveOrderUseCase extends Consumer<Integer> {}
