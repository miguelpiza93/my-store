package com.mapiz.mystore.sales.application.usecase;

import com.mapiz.mystore.sales.application.command.RegisterSaleCommand;
import com.mapiz.mystore.sales.domain.Sale;
import java.util.function.Function;

@FunctionalInterface
public interface RegisterSaleUseCase extends Function<RegisterSaleCommand, Sale> {}
