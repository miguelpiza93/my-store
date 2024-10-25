package com.mapiz.mystore.product.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

  @Autowired protected UnitMapper unitMapper;

  @Mapping(target = "referenceUnit", ignore = true)
  public abstract Product toDomain(ProductEntity productEntity);

  @Mapping(target = "referenceUnit", ignore = true)
  public abstract ProductEntity toEntity(Product product);

  @AfterMapping
  public void postMappingModel(ProductEntity entity, @MappingTarget Product target) {
    if (entity.getReferenceUnit() == null) {
      return;
    }

    Unit unit = unitMapper.entityToModel(entity.getReferenceUnit());
    target.setReferenceUnit(unit);
  }

  @AfterMapping
  public void postMappingEntity(Product source, @MappingTarget ProductEntity target) {
    UnitEntity unit = unitMapper.modelToEntity(source.getReferenceUnit());
    target.setReferenceUnit(unit);
  }
}
