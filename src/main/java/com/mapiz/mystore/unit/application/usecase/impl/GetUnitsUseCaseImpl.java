package com.mapiz.mystore.unit.application.usecase.impl;

import com.mapiz.mystore.unit.application.usecase.GetUnitsUseCase;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.repository.UnitRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUnitsUseCaseImpl implements GetUnitsUseCase {

  private final UnitRepository unitRepository;

  @Override
  public List<Unit> get() {
    return unitRepository.findAll();
  }
}
