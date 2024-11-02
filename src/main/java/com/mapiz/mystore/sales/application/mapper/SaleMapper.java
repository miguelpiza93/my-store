package com.mapiz.mystore.sales.application.mapper;

import com.mapiz.mystore.sales.application.dto.response.RegisterSaleResponse;
import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.domain.SaleLine;
import com.mapiz.mystore.shared.BigDecimalMapper;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BigDecimalMapper.class, SaleLineMapper.class})
public abstract class SaleMapper {

  public static final SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

  public abstract RegisterSaleResponse modelToResponse(Sale model);

  @Mapping(target = "total", expression = "java(calculateTotal(model.getLines()))")
  public abstract SaleSummary modelToSummary(Sale model);

  protected BigDecimal calculateTotal(List<SaleLine> lines) {
    return lines.stream().map(SaleLine::getTotal).reduce(BigDecimal.ZERO, BigDecimalUtils::add);
  }
}
