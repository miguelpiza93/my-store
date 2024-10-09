package com.mapiz.mystore.unit.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.repository.UnitRepository;
import com.mapiz.mystore.unit.infrastructure.persistence.mapper.UnitMapper;
import com.mapiz.mystore.unit.infrastructure.persistence.repository.JpaUnitRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnitRepositoryImpl implements UnitRepository {

  private final JpaUnitRepository jpaUnitRepository;

  @Override
  public List<Unit> findAll() {
    return jpaUnitRepository.findAll().stream()
        .map(unit -> UnitMapper.INSTANCE.entityToModel(unit, new CycleAvoidingMappingContext()))
        .toList();
  }

  @Override
  public List<Unit> findAllById(Set<Integer> unitIds) {
    return jpaUnitRepository.findAllById(unitIds).stream()
        .map(unit -> UnitMapper.INSTANCE.entityToModel(unit, new CycleAvoidingMappingContext()))
        .toList();
  }
}
