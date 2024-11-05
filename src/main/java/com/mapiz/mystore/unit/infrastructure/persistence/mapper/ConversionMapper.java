package com.mapiz.mystore.unit.infrastructure.persistence.mapper;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.UnitConversion;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitConversionEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ConversionMapper {

  @Mappings({
    @Mapping(target = "fromUnit", ignore = true),
    @Mapping(target = "toUnit", ignore = true)
  })
  public abstract UnitConversion entityToModel(
      UnitConversionEntity entity, @Context CycleAvoidingMappingContext context);

  @AfterMapping
  public void postMapping(UnitConversionEntity entity, @MappingTarget UnitConversion target) {
    var toUnitEntity = entity.getToUnit();
    var toUnit =
        Unit.builder()
            .id(toUnitEntity.getId())
            .name(toUnitEntity.getName())
            .symbol(toUnitEntity.getSymbol())
            .isFractional(toUnitEntity.isFractional())
            .isBaseUnit(toUnitEntity.isBaseUnit())
            .build();
    target.setToUnit(toUnit);
  }
}
