package com.mapiz.mystore.stock.application.mapper;

import com.mapiz.mystore.stock.application.dto.StockItemSummaryResponse;
import com.mapiz.mystore.stock.domain.StockItemSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class StockItemMapper {
  public static final StockItemMapper INSTANCE = Mappers.getMapper(StockItemMapper.class);

  @Mapping(target = "vendorProductId", source = "vendorProduct.id")
  @Mapping(target = "productName", source = "vendorProduct.product.name")
  @Mapping(target = "vendorName", source = "vendorProduct.vendor.name")
  public abstract StockItemSummaryResponse modelToResponse(StockItemSummary summary);
}
