package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.vendor.application.dto.ProductVendorResponse;
import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public abstract class ProductVendorMapper {

  public static final ProductVendorMapper INSTANCE = Mappers.getMapper(ProductVendorMapper.class);

  public abstract ProductVendor entityToModel(
      VendorProductEntity entity, @Context CycleAvoidingMappingContext context);

  public abstract VendorProductEntity modelToEntity(ProductVendor model);

  public abstract ProductVendorResponse modelToProductVendorResponse(ProductVendor productVendor);
}
