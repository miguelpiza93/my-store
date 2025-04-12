package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.usecase.base.BaseGroupingStrategyUseCase;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;

@Component
public class GroupByDayStrategyUseCaseImpl extends BaseGroupingStrategyUseCase<ZonedDateTime> {

  @Override
  protected ZonedDateTime getGroupingKey(Instant createdAt) {
    return createdAt.atZone(ZoneId.systemDefault());
  }
}
