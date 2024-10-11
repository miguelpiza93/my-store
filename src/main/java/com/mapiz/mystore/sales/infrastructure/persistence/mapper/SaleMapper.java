package com.mapiz.mystore.sales.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.sales.domain.Sale;
import com.mapiz.mystore.sales.infrastructure.persistence.entity.SaleEntity;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class, UnitMapper.class})
public abstract class SaleMapper {

  public static final SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

  @Mapping(target = "id", ignore = true)
  public abstract SaleEntity modelToEntity(Sale model);

  public abstract Sale entityToModel(
      SaleEntity saleEntity, @Context CycleAvoidingMappingContext context);
}
