package com.mapiz.mystore.sales.infrastructure.persistence.mapper;

import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.infrastructure.persistence.entity.SaleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SaleLineMapper.class})
public interface SaleMapper {

  SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

  @Mapping(target = "id", ignore = true)
  SaleEntity modelToEntity(Sale model);

  @Mapping(target = "lines", ignore = true)
  Sale entityToModel(SaleEntity saleEntity);
}
