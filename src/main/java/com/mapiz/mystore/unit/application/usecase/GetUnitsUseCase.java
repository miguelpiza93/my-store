package com.mapiz.mystore.unit.application.usecase;

import com.mapiz.mystore.unit.domain.Unit;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetUnitsUseCase extends Supplier<List<Unit>> {}
