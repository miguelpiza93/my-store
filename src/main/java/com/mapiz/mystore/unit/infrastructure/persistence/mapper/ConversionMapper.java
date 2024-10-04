package com.mapiz.mystore.unit.infrastructure.persistence.mapper;

import com.mapiz.mystore.unit.domain.Conversion;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.ConversionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ConversionMapper {

  public static final ConversionMapper INSTANCE = Mappers.getMapper(ConversionMapper.class);

  @Mapping(target = "fromUnit", ignore = true)
  @Mapping(target = "toUnit.isFractional", source = "toUnit.fractional")
  @Mapping(target = "toUnit.isBaseUnit", source = "toUnit.baseUnit")
  @Mapping(target = "toUnit.unitConversions", ignore = true)
  public abstract Conversion entityToModel(ConversionEntity entity);
}
