package com.mapiz.mystore.purchaseorder.infrastructure.api;

import static com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant.PURCHASE_ORDER_BASE_PATH;

import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrdersUseCase;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PURCHASE_ORDER_BASE_PATH)
@RequiredArgsConstructor
public class GetPurchaseOrdersController {

  private final GetPurchaseOrdersUseCase getPurchaseOrdersUseCase;

  @GetMapping
  public @ResponseBody ResponseEntity<List<PurchaseOrderResponse>> findAll() {
    var result = getPurchaseOrdersUseCase.get();
    var response =
        result.stream()
            .map(PurchaseOrderMapper.INSTANCE::modelToCreatePurchaseOrderResponse)
            .toList();
    return ResponseEntity.ok(response);
  }
}
