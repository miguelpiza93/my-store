package com.mapiz.mystore.unit.infrastructure.persistence.mapper;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ConversionMapper.class})
public abstract class UnitMapper {
  public static final UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);

  @Mapping(source = "baseUnit", target = "isBaseUnit")
  @Mapping(source = "fractional", target = "isFractional")
  public abstract Unit entityToModel(
      UnitEntity entity, @Context CycleAvoidingMappingContext context);

  @Mapping(source = "baseUnit", target = "isBaseUnit")
  @Mapping(source = "fractional", target = "isFractional")
  public abstract UnitEntity modelToEntity(
      Unit model, @Context CycleAvoidingMappingContext context);
}
