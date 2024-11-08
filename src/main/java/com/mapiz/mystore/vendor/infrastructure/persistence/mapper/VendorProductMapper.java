package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.UnitConversion;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import com.mapiz.mystore.vendor.application.dto.VendorProductResponse;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import java.util.Optional;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class VendorProductMapper {

  @Autowired protected UnitMapper unitMapper;
  @Autowired protected ProductMapper productMapper;

  @Mappings({
    @Mapping(target = "salePrices", ignore = true),
    @Mapping(target = "product", ignore = true)
  })
  public abstract VendorProduct entityToModel(VendorProductEntity entity);

  @Mappings({
    @Mapping(target = "salePrices", ignore = true),
    @Mapping(target = "product", ignore = true)
  })
  public abstract VendorProductEntity modelToEntity(VendorProduct model);

  @Mapping(target = "product.baseUnitSymbol", ignore = true)
  public abstract VendorProductResponse modelToProductVendorResponse(VendorProduct productVendor);

  @AfterMapping
  public void postMappingModel(VendorProductEntity entity, @MappingTarget VendorProduct target) {
    setProductToModel(entity, target);
    setPrices(entity, target);
  }

  @AfterMapping
  public void postMappingResponse(
      VendorProduct source, @MappingTarget VendorProductResponse target) {
    var baseUnitSymbol =
        Optional.ofNullable(source.getProduct().getReferenceUnit())
            .map(Unit::getBaseConversion)
            .map(UnitConversion::getToUnit)
            .map(Unit::getSymbol)
            .orElse(null);
    target.getProduct().setBaseUnitSymbol(baseUnitSymbol);
  }

  private void setPrices(VendorProductEntity entity, VendorProduct target) {
    if (entity.getSalePrices() == null) {
      return;
    }
    target.setSalePrices(
        entity.getSalePrices().stream()
            .map(
                vendorProductUnitVariantEntity ->
                    VendorProductUnitVariant.builder()
                        .id(vendorProductUnitVariantEntity.getId())
                        .vendorProduct(target)
                        .unit(unitMapper.entityToModel(vendorProductUnitVariantEntity.getUnit()))
                        .salePrice(vendorProductUnitVariantEntity.getSalePrice())
                        .build())
            .toList());
  }

  @AfterMapping
  public void postMappingEntity(VendorProduct source, @MappingTarget VendorProductEntity target) {
    setProductToEntity(source, target);
  }

  private void setProductToModel(VendorProductEntity entity, VendorProduct target) {
    Product product = productMapper.toDomain(entity.getProduct());
    target.setProduct(product);
  }

  private void setProductToEntity(VendorProduct source, VendorProductEntity target) {
    ProductEntity product = productMapper.toEntity(source.getProduct());
    target.setProduct(product);
  }
}
