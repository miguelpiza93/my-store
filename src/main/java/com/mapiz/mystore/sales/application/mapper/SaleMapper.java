package com.mapiz.mystore.sales.application.mapper;

import com.mapiz.mystore.sales.application.dto.request.RegisterSaleResponse;
import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.shared.BigDecimalMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BigDecimalMapper.class, SaleLineMapper.class})
public interface SaleMapper {

  SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

  RegisterSaleResponse modelToResponse(Sale model);
}
