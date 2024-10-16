package com.mapiz.mystore.product.application.mapper;

import com.mapiz.mystore.product.application.dto.ProductPriceDetail;
import com.mapiz.mystore.product.domain.ProductPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ProductPriceMapper {

  public static final ProductPriceMapper INSTANCE = Mappers.getMapper(ProductPriceMapper.class);

  @Mapping(source = "unit.id", target = "unitId")
  @Mapping(source = "unit.name", target = "unitName")
  @Mapping(source = "salePrice", target = "price")
  public abstract ProductPriceDetail modelToResponse(ProductPrice productPrice);
}
