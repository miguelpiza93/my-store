package com.mapiz.mystore.stock.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public abstract class StockItemMapper {
  public static final StockItemMapper INSTANCE = Mappers.getMapper(StockItemMapper.class);

  public abstract StockItemEntity modelToEntity(StockItem stockItem);

  public abstract StockItem entityToModel(
      StockItemEntity entity, @Context CycleAvoidingMappingContext context);
}
