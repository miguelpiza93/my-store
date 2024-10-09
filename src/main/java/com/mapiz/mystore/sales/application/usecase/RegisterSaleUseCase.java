package com.mapiz.mystore.sales.application.usecase;

import com.mapiz.mystore.sales.application.command.RegisterSaleCommand;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface RegisterSaleUseCase extends Function<RegisterSaleCommand, List<Integer>> {}
