package com.mapiz.mystore.unit.domain.repository;

import com.mapiz.mystore.unit.domain.Unit;
import java.util.List;

public interface UnitRepository {
  List<Unit> findAll();
}
