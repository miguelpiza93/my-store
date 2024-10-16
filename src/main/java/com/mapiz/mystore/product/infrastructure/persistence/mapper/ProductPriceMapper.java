package com.mapiz.mystore.product.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.domain.ProductPrice;
import com.mapiz.mystore.product.infrastructure.persistence.ProductPriceEntity;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UnitMapper.class, ProductMapper.class})
public abstract class ProductPriceMapper {

  public static final ProductPriceMapper INSTANCE = Mappers.getMapper(ProductPriceMapper.class);

  public abstract ProductPriceEntity toEntity(ProductPrice productPrice);

  public abstract ProductPrice toDomain(
      ProductPriceEntity productPriceEntity, @Context CycleAvoidingMappingContext context);
}
