package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductUnitVariantEntity;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class VendorProductUnitVariantMapper {

  @Autowired protected VendorProductMapper vendorProductMapper;
  @Autowired protected UnitMapper unitMapper;

  @Mappings({
    @Mapping(target = "vendorProduct", ignore = true),
    @Mapping(target = "unit", ignore = true)
  })
  public abstract VendorProductUnitVariantEntity toEntity(VendorProductUnitVariant productPrice);

  @Mappings({
    @Mapping(target = "vendorProduct", ignore = true),
    @Mapping(target = "unit", ignore = true)
  })
  public abstract VendorProductUnitVariant toDomain(
      VendorProductUnitVariantEntity productPriceEntity);

  @AfterMapping
  public void postMappingModel(
      VendorProductUnitVariantEntity source, @MappingTarget VendorProductUnitVariant target) {
    VendorProduct vendorProduct = vendorProductMapper.entityToModel(source.getVendorProduct());
    Unit unit = unitMapper.entityToModel(source.getUnit());
    target.setVendorProduct(vendorProduct);
    target.setUnit(unit);
  }

  @AfterMapping
  public void postMappingEntity(
      VendorProductUnitVariant source, @MappingTarget VendorProductUnitVariantEntity target) {
    VendorProductEntity vendorProduct =
        vendorProductMapper.modelToEntity(source.getVendorProduct());
    UnitEntity unit = unitMapper.modelToEntity(source.getUnit());
    target.setVendorProduct(vendorProduct);
    target.setUnit(unit);
  }
}
