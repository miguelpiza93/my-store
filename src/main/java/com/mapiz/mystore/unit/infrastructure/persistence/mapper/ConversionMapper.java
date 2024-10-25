package com.mapiz.mystore.unit.infrastructure.persistence.mapper;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.domain.Conversion;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.ConversionEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ConversionMapper {

  @Mappings({
    @Mapping(target = "fromUnit", ignore = true),
    @Mapping(target = "toUnit", ignore = true)
  })
  public abstract Conversion entityToModel(
      ConversionEntity entity, @Context CycleAvoidingMappingContext context);

  @AfterMapping
  public void postMapping(ConversionEntity entity, @MappingTarget Conversion target) {
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
