package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.vendor.application.dto.VendorVendorResponse;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public abstract class ProductVendorMapper {

  public static final ProductVendorMapper INSTANCE = Mappers.getMapper(ProductVendorMapper.class);

  public abstract VendorProduct entityToModel(
      VendorProductEntity entity, @Context CycleAvoidingMappingContext context);

  public abstract VendorProductEntity modelToEntity(VendorProduct model);

  public abstract VendorVendorResponse modelToProductVendorResponse(VendorProduct productVendor);
}
