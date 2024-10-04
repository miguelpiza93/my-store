package com.mapiz.mystore.stock.infrastructure.api;

import static com.mapiz.mystore.stock.infrastructure.EndpointConstant.STOCK_BASE_PATH;

import com.mapiz.mystore.stock.application.dto.StockItemResponse;
import com.mapiz.mystore.stock.application.mapper.StockItemMapper;
import com.mapiz.mystore.stock.application.usecase.GetProductsInStockUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(STOCK_BASE_PATH)
public class GetStockProductsController {

  private final GetProductsInStockUseCase getProductsInStockUseCase;

  @GetMapping
  public @ResponseBody ResponseEntity<List<StockItemResponse>> getStock() {
    var products = getProductsInStockUseCase.get();
    var response = products.stream().map(StockItemMapper.INSTANCE::modelToResponse).toList();
    return ResponseEntity.ok(response);
  }
}
