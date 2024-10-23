package com.mapiz.mystore.vendor.application.mapper;

import com.mapiz.mystore.vendor.application.dto.VendorProductDetailResponse;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = VendorProductUnitVariantMapper.class)
public abstract class VendorProductMapper {

  public static final VendorProductMapper INSTANCE = Mappers.getMapper(VendorProductMapper.class);

  @Mapping(target = "referenceUnit", source = "product.referenceUnit.name")
  @Mapping(target = "name", source = "product.name")
  @Mapping(target = "description", source = "product.description")
  @Mapping(target = "vendorName", source = "vendor.name")
  public abstract VendorProductDetailResponse modelToDetailResponse(VendorProduct vendorProduct);
}
