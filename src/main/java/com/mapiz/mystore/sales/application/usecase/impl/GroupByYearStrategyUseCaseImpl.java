package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.usecase.BaseGroupingStrategyUseCase;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class GroupByYearStrategyUseCaseImpl extends BaseGroupingStrategyUseCase<Integer> {

  @Override
  protected Integer getGroupingKey(Instant createdAt) {
    return createdAt.atZone(ZoneId.systemDefault()).getYear();
  }
}
