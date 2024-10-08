package com.mapiz.mystore.stock.application.usecase;

import com.mapiz.mystore.stock.application.dto.StockItemSummary;
import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetStockSummaryUseCase extends Supplier<Collection<StockItemSummary>> {}
