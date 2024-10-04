package com.mapiz.mystore.product.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UnitMapper.class})
public abstract class ProductMapper {

  public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  public abstract Product toDomain(
      ProductEntity productEntity, @Context CycleAvoidingMappingContext context);

  public abstract ProductEntity toEntity(Product product);
}
