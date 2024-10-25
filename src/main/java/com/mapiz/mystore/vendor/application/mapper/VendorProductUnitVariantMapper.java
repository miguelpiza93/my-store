package com.mapiz.mystore.vendor.application.mapper;

import com.mapiz.mystore.vendor.application.dto.VendorProductPriceDetail;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface VendorProductUnitVariantMapper {

  VendorProductMapper INSTANCE = Mappers.getMapper(VendorProductMapper.class);

  @Mapping(source = "unit.id", target = "unitId")
  @Mapping(source = "unit.name", target = "unitName")
  @Mapping(source = "salePrice", target = "price")
  VendorProductPriceDetail modelToResponse(VendorProductUnitVariant variant);
}
