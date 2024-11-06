package com.mapiz.mystore.unit.application.mapper;

import com.mapiz.mystore.unit.application.command.CreateUnitCommand;
import com.mapiz.mystore.unit.application.dto.CreateUnitRequest;
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

  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "unitConversions", ignore = true),
    @Mapping(source = "baseUnit", target = "isBaseUnit"),
    @Mapping(source = "fractional", target = "isFractional")
  })
  Unit commandToModel(CreateUnitCommand command);

  CreateUnitCommand requestToCommand(CreateUnitRequest request);
}
