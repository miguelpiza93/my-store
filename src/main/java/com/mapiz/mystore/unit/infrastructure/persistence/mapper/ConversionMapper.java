package com.mapiz.mystore.unit.infrastructure.persistence.mapper;

import com.mapiz.mystore.unit.domain.Conversion;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.ConversionEntity;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public abstract class ConversionMapper {

  public static final ConversionMapper INSTANCE = Mappers.getMapper(ConversionMapper.class);

  @Mapping(target = "fromUnit.unitConversions", ignore = true)
  public abstract Conversion entityToModel(ConversionEntity entity);
}
