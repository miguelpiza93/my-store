package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductUnitVariantEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    componentModel = "spring",
    uses = {UnitMapper.class})
public abstract class VendorProductUnitVariantMapper {

  @Autowired protected VendorProductMapper vendorProductMapper;

  public abstract VendorProductUnitVariantEntity toEntity(VendorProductUnitVariant productPrice);

  @Mapping(target = "vendorProduct", ignore = true)
  public abstract VendorProductUnitVariant toDomain(
      VendorProductUnitVariantEntity productPriceEntity);

  @AfterMapping
  public void postMappingModel(
      VendorProductUnitVariantEntity source, @MappingTarget VendorProductUnitVariant target) {
    VendorProduct vendorProduct = vendorProductMapper.entityToModel(source.getVendorProduct());
    target.setVendorProduct(vendorProduct);
  }
}
