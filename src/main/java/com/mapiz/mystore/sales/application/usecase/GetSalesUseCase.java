package com.mapiz.mystore.sales.application.usecase;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetSalesUseCase extends Supplier<List<SaleSummary>> {}
