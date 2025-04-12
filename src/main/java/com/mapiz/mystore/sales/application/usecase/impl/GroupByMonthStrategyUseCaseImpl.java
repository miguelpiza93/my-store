package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.usecase.base.BaseGroupingStrategyUseCase;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class GroupByMonthStrategyUseCaseImpl extends BaseGroupingStrategyUseCase<String> {

  @Override
  protected String getGroupingKey(Instant createdAt) {
    LocalDate date = createdAt.atZone(ZoneId.systemDefault()).toLocalDate();
    return date.getYear() + "-" + date.getMonthValue();
  }
}
