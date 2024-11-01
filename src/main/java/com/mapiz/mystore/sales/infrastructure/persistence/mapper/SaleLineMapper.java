package com.mapiz.mystore.sales.infrastructure.persistence.mapper;

import com.mapiz.mystore.sales.domain.SaleLine;
import com.mapiz.mystore.sales.infrastructure.persistence.entity.SaleLineEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductUnitVariantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {VendorProductUnitVariantMapper.class})
public abstract class SaleLineMapper {

  @Mapping(target = "id", ignore = true)
  public abstract SaleLineEntity modelToEntity(SaleLine model);

  @Mapping(target = "sale", ignore = true)
  public abstract SaleLine entityToModel(SaleLineEntity saleEntity);
}
