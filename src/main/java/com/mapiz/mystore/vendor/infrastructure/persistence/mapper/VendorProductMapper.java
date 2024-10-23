package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.vendor.application.dto.VendorProductResponse;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class, VendorProductUnitMapper.class, VendorMapper.class})
public abstract class VendorProductMapper {

  public static final VendorProductMapper INSTANCE = Mappers.getMapper(VendorProductMapper.class);

  public abstract VendorProduct entityToModel(
      VendorProductEntity entity, @Context CycleAvoidingMappingContext context);

  @Mapping(target = "salePrices", ignore = true)
  public abstract VendorProductEntity modelToEntity(VendorProduct model);

  public abstract VendorProductResponse modelToProductVendorResponse(VendorProduct productVendor);
}
