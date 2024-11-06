package com.mapiz.mystore.unit.application.usecase;

import com.mapiz.mystore.unit.application.command.CreateUnitCommand;
import com.mapiz.mystore.unit.domain.Unit;
import java.util.function.Function;

@FunctionalInterface
public interface CreateUnitUseCase extends Function<CreateUnitCommand, Unit> {}
