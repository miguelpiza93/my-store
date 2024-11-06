package com.mapiz.mystore.unit.application.usecase.impl;

import com.mapiz.mystore.unit.application.command.CreateUnitCommand;
import com.mapiz.mystore.unit.application.mapper.UnitMapper;
import com.mapiz.mystore.unit.application.usecase.CreateUnitUseCase;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.unit.domain.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUnitUseCaseImpl implements CreateUnitUseCase {

  private final UnitRepository repository;

  @Override
  public Unit apply(CreateUnitCommand command) {
    var model = UnitMapper.INSTANCE.commandToModel(command);
    return repository.save(model);
  }
}
