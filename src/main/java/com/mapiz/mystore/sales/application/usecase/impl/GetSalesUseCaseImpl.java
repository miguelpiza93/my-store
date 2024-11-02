package com.mapiz.mystore.sales.application.usecase.impl;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import com.mapiz.mystore.sales.application.mapper.SaleMapper;
import com.mapiz.mystore.sales.application.usecase.GetSalesUseCase;
import com.mapiz.mystore.sales.domain.repository.SaleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetSalesUseCaseImpl implements GetSalesUseCase {

  private final SaleRepository saleRepository;

  @Override
  public List<SaleSummary> get() {
    return saleRepository.findAll().stream().map(SaleMapper.INSTANCE::modelToSummary).toList();
  }
}
