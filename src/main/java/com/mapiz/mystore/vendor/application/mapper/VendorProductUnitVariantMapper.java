package com.mapiz.mystore.vendor.application.mapper;

import com.mapiz.mystore.vendor.application.dto.VendorProductPriceDetail;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class VendorProductUnitVariantMapper {

  public static final VendorProductUnitVariantMapper INSTANCE =
      Mappers.getMapper(VendorProductUnitVariantMapper.class);

  @Mapping(source = "unit.id", target = "unitId")
  @Mapping(source = "unit.name", target = "unitName")
  @Mapping(source = "salePrice", target = "price")
  public abstract VendorProductPriceDetail modelToResponse(VendorProductUnitVariant variant);
}
