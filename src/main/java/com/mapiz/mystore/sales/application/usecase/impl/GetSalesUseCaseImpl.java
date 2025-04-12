package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import com.mapiz.mystore.sales.application.mapper.SaleMapper;
import com.mapiz.mystore.sales.application.usecase.GetSalesUseCase;
import com.mapiz.mystore.sales.application.usecase.base.BaseGroupingStrategyUseCase;
import com.mapiz.mystore.sales.domain.GroupOptions;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetSalesUseCaseImpl implements GetSalesUseCase {

  private final SaleRepository saleRepository;
  private final GroupingStrategySelectorUseCaseImpl strategySelector;

  @Override
  public List<SaleSummary> apply(GroupOptions groupOption) {
    var sales = saleRepository.findAll().stream().map(SaleMapper.INSTANCE::modelToSummary);
    BaseGroupingStrategyUseCase<?> groupingStrategy = strategySelector.select(groupOption);
    Map<?, List<SaleSummary>> groupedSales = groupingStrategy.apply(sales.toList());
    return groupedSales.values().stream().map(this::summarizeGroup).toList();
  }

  private SaleSummary summarizeGroup(List<SaleSummary> sales) {
    if (sales.size() == 1) {
      return sales.get(0);
    }

    BigDecimal total =
        sales.stream().map(SaleSummary::getTotal).reduce(BigDecimal.ZERO, BigDecimalUtils::add);
    return SaleSummary.builder().total(total).createdAt(sales.get(0).getCreatedAt()).build();
  }
}
