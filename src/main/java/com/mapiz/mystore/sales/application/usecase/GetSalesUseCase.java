package com.mapiz.mystore.sales.application.usecase;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import com.mapiz.mystore.sales.domain.GroupOptions;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface GetSalesUseCase extends Function<GroupOptions, List<SaleSummary>> {}
