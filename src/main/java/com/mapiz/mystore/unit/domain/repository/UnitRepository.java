package com.mapiz.mystore.unit.domain.repository;

import com.mapiz.mystore.unit.domain.Unit;
import java.util.List;
import java.util.Set;

public interface UnitRepository {
  List<Unit> findAll();

  List<Unit> findAllById(Set<Integer> unitIds);
}
