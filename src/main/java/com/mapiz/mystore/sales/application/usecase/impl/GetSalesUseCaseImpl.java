package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import com.mapiz.mystore.sales.application.mapper.SaleMapper;
import com.mapiz.mystore.sales.application.usecase.GetSalesUseCase;
import com.mapiz.mystore.sales.domain.GroupOptions;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetSalesUseCaseImpl implements GetSalesUseCase {

  private final SaleRepository saleRepository;

  @Override
  public List<SaleSummary> apply(GroupOptions groupOption) {
    var sales = saleRepository.findAll().stream().map(SaleMapper.INSTANCE::modelToSummary);

    if (groupOption == GroupOptions.DAY) {
      return sales.toList();
    }
    Map<?, List<SaleSummary>> groupedSales;
    if (groupOption == GroupOptions.MONTH) {
      groupedSales =
          sales.collect(Collectors.groupingBy(sale -> extractYearMonth(sale.getCreatedAt())));
    } else if (groupOption == GroupOptions.YEAR) {
      groupedSales = sales.collect(Collectors.groupingBy(sale -> extractYear(sale.getCreatedAt())));
    } else {
      throw new IllegalArgumentException("Invalid group option");
    }
    return groupedSales.values().stream().map(this::summarizeGroup).toList();
  }

  private String extractYearMonth(Instant createdAt) {
    LocalDate date = createdAt.atZone(ZoneId.systemDefault()).toLocalDate();
    return date.getYear() + "-" + date.getMonthValue();
  }

  private Integer extractYear(Instant createdAt) {
    return createdAt.atZone(ZoneId.systemDefault()).getYear();
  }

  private SaleSummary summarizeGroup(List<SaleSummary> sales) {
    BigDecimal total =
        sales.stream().map(SaleSummary::getTotal).reduce(BigDecimal.ZERO, BigDecimalUtils::add);
    return SaleSummary.builder().total(total).createdAt(sales.get(0).getCreatedAt()).build();
  }
}
