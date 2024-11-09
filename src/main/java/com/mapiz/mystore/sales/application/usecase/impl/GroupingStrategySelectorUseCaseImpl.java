package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.usecase.BaseGroupingStrategyUseCase;
import com.mapiz.mystore.sales.domain.GroupOptions;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GroupingStrategySelectorUseCaseImpl {

  private final Map<GroupOptions, BaseGroupingStrategyUseCase<?>> strategies;

  public GroupingStrategySelectorUseCaseImpl(
      GroupByDayStrategyUseCaseImpl groupByDayStrategyUseCase,
      GroupByMonthStrategyUseCaseImpl groupByMonthStrategyUseCase,
      GroupByYearStrategyUseCaseImpl groupByYearStrategyUseCase) {
    this.strategies =
        Map.of(
            GroupOptions.DAY, groupByDayStrategyUseCase,
            GroupOptions.MONTH, groupByMonthStrategyUseCase,
            GroupOptions.YEAR, groupByYearStrategyUseCase);
  }

  public BaseGroupingStrategyUseCase<?> select(GroupOptions groupOption) {
    return strategies.get(groupOption);
  }
}
