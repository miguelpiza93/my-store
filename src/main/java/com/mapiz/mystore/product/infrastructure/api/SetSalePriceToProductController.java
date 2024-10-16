package com.mapiz.mystore.product.infrastructure.api;

import static com.mapiz.mystore.product.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.product.application.dto.SetSalePriceToStockProductRequest;
import com.mapiz.mystore.product.application.usecase.SetSalePriceToProductUseCase;
import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class SetSalePriceToProductController {

  private final SetSalePriceToProductUseCase setSalePriceToProductUseCase;

  @PatchMapping("/{id}")
  public @ResponseBody ResponseEntity<Void> setSalePrice(
      @PathVariable Integer id, @Valid @RequestBody SetSalePriceToStockProductRequest request) {
    setSalePriceToProductUseCase.accept(
        SetSalePriceToStockProductCommand.builder()
            .productId(id)
            .unitId(request.getUnitId())
            .salePrice(request.getSalePrice())
            .build());
    return ResponseEntity.accepted().build();
  }
}
