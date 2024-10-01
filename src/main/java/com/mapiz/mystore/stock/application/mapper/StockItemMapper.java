package com.mapiz.mystore.stock.application.mapper;

import com.mapiz.mystore.stock.application.dto.StockItemResponse;
import com.mapiz.mystore.stock.domain.StockItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class StockItemMapper {

  public static final StockItemMapper INSTANCE = Mappers.getMapper(StockItemMapper.class);

  @Mapping(target = "productName", source = "product.name")
  public abstract StockItemResponse modelToResponse(StockItem product);
}
