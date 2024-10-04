package com.mapiz.mystore.unit.application.mapper;

import com.mapiz.mystore.unit.application.dto.UnitResponse;
import com.mapiz.mystore.unit.domain.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UnitMapper {

  public static final UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);

  @Mapping(source = "baseUnit", target = "isBaseUnit")
  @Mapping(source = "fractional", target = "isFractional")
  public abstract UnitResponse modelToResponse(Unit unit);
}
