package com.mapiz.mystore.sales.application.usecase;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseGroupingStrategyUseCase<T> implements GroupingStrategyUseCase {

  @Override
  public Map<?, List<SaleSummary>> apply(List<SaleSummary> sales) {
    return sales.stream()
        .collect(Collectors.groupingBy(sale -> getGroupingKey(sale.getCreatedAt())));
  }

  protected abstract T getGroupingKey(Instant createdAt);
}
