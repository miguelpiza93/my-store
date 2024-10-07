package com.mapiz.mystore.stock.application.usecase;

import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import java.util.function.Consumer;

@FunctionalInterface
public interface SetSalePriceToStockProductUseCase
    extends Consumer<SetSalePriceToStockProductCommand> {}
