package com.mapiz.mystore.sales.infrastructure.api;

import static com.mapiz.mystore.sales.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.sales.application.dto.request.RegisterSaleRequest;
import com.mapiz.mystore.sales.application.dto.request.RegisterSaleResponse;
import com.mapiz.mystore.sales.application.mapper.SaleLineMapper;
import com.mapiz.mystore.sales.application.mapper.SaleMapper;
import com.mapiz.mystore.sales.application.usecase.RegisterSaleUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class RegisterSaleController {

  private final RegisterSaleUseCase useCase;

  @PostMapping
  public ResponseEntity<RegisterSaleResponse> registerSale(
      @Valid @RequestBody RegisterSaleRequest request) {
    var command = SaleLineMapper.INSTANCE.requestToCommand(request);
    var savedSale = useCase.apply(command);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(SaleMapper.INSTANCE.modelToResponse(savedSale));
  }
}
