package com.mapiz.mystore.stock.infrastructure.api;

import static com.mapiz.mystore.stock.infrastructure.EndpointConstant.STOCK_BASE_PATH;

import com.mapiz.mystore.stock.application.dto.StockItemSummaryResponse;
import com.mapiz.mystore.stock.application.mapper.StockItemMapper;
import com.mapiz.mystore.stock.application.usecase.GetStockSummaryUseCase;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(STOCK_BASE_PATH)
public class GetStockSummaryController {

  private final GetStockSummaryUseCase getStockSummaryUseCase;

  @GetMapping("/summary")
  public @ResponseBody ResponseEntity<Collection<StockItemSummaryResponse>> getStock() {
    var summary = getStockSummaryUseCase.get();
    var response = summary.stream().map(StockItemMapper.INSTANCE::modelToResponse).toList();
    return ResponseEntity.ok(response);
  }
}
