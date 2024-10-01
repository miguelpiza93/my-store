package com.mapiz.mystore.stock.infrastructure.persistence.mapper;

import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class StockItemMapper {
  public static final StockItemMapper INSTANCE = Mappers.getMapper(StockItemMapper.class);

  public abstract StockItemEntity modelToEntity(StockItem stockItem);

  public abstract StockItem entityToModel(StockItemEntity entity);
}
