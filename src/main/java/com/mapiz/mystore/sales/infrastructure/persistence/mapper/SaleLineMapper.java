package com.mapiz.mystore.sales.infrastructure.persistence.mapper;

import com.mapiz.mystore.sales.domain.SaleLine;
import com.mapiz.mystore.sales.infrastructure.persistence.entity.SaleLineEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductUnitVariantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {VendorProductUnitVariantMapper.class})
public interface SaleLineMapper {

  SaleLineMapper INSTANCE = Mappers.getMapper(SaleLineMapper.class);

  @Mapping(target = "id", ignore = true)
  SaleLineEntity modelToEntity(SaleLine model);

  @Mapping(target = "sale", ignore = true)
  SaleLine entityToModel(SaleLineEntity saleEntity);
}
