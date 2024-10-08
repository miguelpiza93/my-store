package com.mapiz.mystore.product.application.usecase;

import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import java.util.function.Consumer;

@FunctionalInterface
public interface SetSalePriceToProductUseCase extends Consumer<SetSalePriceToStockProductCommand> {}
