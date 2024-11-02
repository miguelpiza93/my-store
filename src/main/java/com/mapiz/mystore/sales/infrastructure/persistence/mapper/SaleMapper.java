package com.mapiz.mystore.sales.infrastructure.persistence.mapper;

import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.infrastructure.persistence.entity.SaleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {SaleLineMapper.class})
public interface SaleMapper {

  @Mapping(target = "id", ignore = true)
  SaleEntity modelToEntity(Sale model);

  Sale entityToModel(SaleEntity saleEntity);
}
