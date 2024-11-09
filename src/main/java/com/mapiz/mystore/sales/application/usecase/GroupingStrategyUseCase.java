package com.mapiz.mystore.sales.application.usecase;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface GroupingStrategyUseCase
    extends Function<List<SaleSummary>, Map<?, List<SaleSummary>>> {}
