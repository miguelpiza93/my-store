package com.mapiz.mystore.stock.application.mapper;

import com.mapiz.mystore.stock.application.dto.StockItemSummaryResponse;
import com.mapiz.mystore.stock.domain.StockItemSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class StockItemMapper {
  public static final StockItemMapper INSTANCE = Mappers.getMapper(StockItemMapper.class);

  @Mappings({
    @Mapping(target = "vendorProductId", source = "vendorProduct.id"),
    @Mapping(target = "productId", source = "vendorProduct.product.id"),
    @Mapping(target = "vendorId", source = "vendorProduct.vendor.id"),
    @Mapping(target = "fullDescription", expression = "java(getFullDescription(summary))")
  })
  public abstract StockItemSummaryResponse modelToResponse(StockItemSummary summary);

  protected String getFullDescription(StockItemSummary summary) {
    String format = "%s %s - %s";
    var product = summary.getVendorProduct().getProduct();
    var vendor = summary.getVendorProduct().getVendor();
    return format.formatted(product.getName(), product.getDescription(), vendor.getName());
  }
}
