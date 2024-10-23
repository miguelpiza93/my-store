package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductUnitVariantEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UnitMapper.class})
public abstract class VendorProductUnitMapper {

  public static final VendorProductUnitMapper INSTANCE =
      Mappers.getMapper(VendorProductUnitMapper.class);

  public abstract VendorProductUnitVariantEntity toEntity(
      VendorProductUnitVariant productPrice, @Context CycleAvoidingMappingContext context);

  public abstract VendorProductUnitVariant toDomain(
      VendorProductUnitVariantEntity productPriceEntity,
      @Context CycleAvoidingMappingContext context);
}
