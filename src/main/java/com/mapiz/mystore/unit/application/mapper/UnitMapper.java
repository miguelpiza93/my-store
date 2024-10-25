package com.mapiz.mystore.unit.application.mapper;

import com.mapiz.mystore.unit.application.dto.UnitResponse;
import com.mapiz.mystore.unit.domain.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UnitMapper {

  UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);

  @Mappings({
    @Mapping(source = "baseUnit", target = "isBaseUnit"),
    @Mapping(source = "fractional", target = "isFractional")
  })
  UnitResponse modelToResponse(Unit unit);
}
