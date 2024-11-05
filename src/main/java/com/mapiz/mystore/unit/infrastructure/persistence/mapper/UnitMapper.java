package com.mapiz.mystore.unit.infrastructure.persistence.mapper;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.UnitConversion;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import java.util.List;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UnitMapper {

  @Autowired protected ConversionMapper conversionMapper;

  @Mappings({
    @Mapping(target = "unitConversions", ignore = true),
    @Mapping(source = "baseUnit", target = "isBaseUnit"),
    @Mapping(source = "fractional", target = "isFractional")
  })
  public abstract Unit entityToModel(UnitEntity entity);

  @Mappings({
    @Mapping(target = "unitConversions", ignore = true),
    @Mapping(source = "baseUnit", target = "isBaseUnit"),
    @Mapping(source = "fractional", target = "isFractional")
  })
  public abstract UnitEntity modelToEntity(Unit model);

  @AfterMapping
  public void postMapping(UnitEntity entity, @MappingTarget Unit target) {
    if (entity.getUnitConversions() == null) {
      return;
    }

    var context = new CycleAvoidingMappingContext();
    List<UnitConversion> conversions =
        entity.getUnitConversions().stream()
            .map(conversion -> conversionMapper.entityToModel(conversion, context))
            .peek(item -> item.setFromUnit(target))
            .toList();
    target.setUnitConversions(conversions);
  }
}
