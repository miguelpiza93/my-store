package com.mapiz.mystore.purchaseorder.infrastructure.api;

import static com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant.PURCHASE_ORDER_BASE_PATH;

import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.application.usecase.CreatePurchaseOrderUseCase;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PURCHASE_ORDER_BASE_PATH)
@RequiredArgsConstructor
public class CreatePurchaseOrderController {

  private final CreatePurchaseOrderUseCase createPurchaseOrderUseCase;

  @PostMapping
  public @ResponseBody ResponseEntity<PurchaseOrderResponse> create(
      @RequestBody CreatePurchaseOrderRequest request) {
    var command = PurchaseOrderMapper.INSTANCE.createPurchaseOrderRequestToCommand(request);
    var result = createPurchaseOrderUseCase.apply(command);
    var response = PurchaseOrderMapper.INSTANCE.modelToCreatePurchaseOrderResponse(result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
