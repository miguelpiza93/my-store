package com.mapiz.mystore.stock.application.usecase;

import com.mapiz.mystore.stock.domain.StockItem;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetProductsInStockUseCase extends Supplier<List<StockItem>> {}
