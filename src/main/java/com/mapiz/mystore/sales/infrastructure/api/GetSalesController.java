package com.mapiz.mystore.sales.infrastructure.api;

import static com.mapiz.mystore.sales.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import com.mapiz.mystore.sales.application.usecase.GetSalesUseCase;
import com.mapiz.mystore.sales.domain.GroupOptions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class GetSalesController {

  private final GetSalesUseCase useCase;

  @GetMapping
  public ResponseEntity<List<SaleSummary>> registerSale(@RequestParam GroupOptions groupBy) {
    return ResponseEntity.status(HttpStatus.OK).body(useCase.apply(groupBy));
  }
}
