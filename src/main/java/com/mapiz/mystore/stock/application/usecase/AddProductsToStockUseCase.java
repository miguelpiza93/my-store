package com.mapiz.mystore.stock.application.usecase;

import java.util.function.Consumer;

@FunctionalInterface
public interface AddProductsToStockUseCase extends Consumer<Integer> {}
