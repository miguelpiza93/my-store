package com.mapiz.mystore.stock.infrastructure.api;

import static com.mapiz.mystore.stock.infrastructure.EndpointConstant.STOCK_BASE_PATH;

import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import com.mapiz.mystore.stock.application.dto.SetSalePriceToStockProductRequest;
import com.mapiz.mystore.stock.application.dto.StockItemResponse;
import com.mapiz.mystore.stock.application.usecase.SetSalePriceToStockProductUseCase;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(STOCK_BASE_PATH)
public class SetSalePriceToStockItemController {

  private final SetSalePriceToStockProductUseCase setSalePriceToStockProductUseCase;

  @PostMapping("/{id}")
  public @ResponseBody ResponseEntity<List<StockItemResponse>> getStock(
      @PathVariable Integer id, @Valid @RequestBody SetSalePriceToStockProductRequest request) {
    setSalePriceToStockProductUseCase.accept(
        SetSalePriceToStockProductCommand.builder()
            .stockId(id)
            .salePrice(request.getSalePrice())
            .build());
    return ResponseEntity.accepted().build();
  }
}
