package com.mapiz.mystore.unit.infrastructure.api;

import static com.mapiz.mystore.unit.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.unit.application.dto.CreateUnitRequest;
import com.mapiz.mystore.unit.application.dto.UnitResponse;
import com.mapiz.mystore.unit.application.mapper.UnitMapper;
import com.mapiz.mystore.unit.application.usecase.CreateUnitUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class CreateUnitController {

  private final CreateUnitUseCase useCase;

  @PostMapping
  public @ResponseBody ResponseEntity<UnitResponse> create(
      @Valid @RequestBody CreateUnitRequest request) {
    var command = UnitMapper.INSTANCE.requestToCommand(request);
    var result = useCase.apply(command);
    var response = UnitMapper.INSTANCE.modelToResponse(result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
