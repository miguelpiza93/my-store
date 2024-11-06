package com.mapiz.mystore.unit.infrastructure.api;

import static com.mapiz.mystore.unit.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.unit.application.dto.UnitResponse;
import com.mapiz.mystore.unit.application.mapper.UnitMapper;
import com.mapiz.mystore.unit.application.usecase.GetUnitsUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class GetUnitsController {

  private final GetUnitsUseCase getUnitsUseCase;

  @GetMapping
  public @ResponseBody ResponseEntity<List<UnitResponse>> getUnits() {
    var result = getUnitsUseCase.get();
    var response = result.stream().map(UnitMapper.INSTANCE::modelToResponse).toList();
    return ResponseEntity.ok(response);
  }
}
