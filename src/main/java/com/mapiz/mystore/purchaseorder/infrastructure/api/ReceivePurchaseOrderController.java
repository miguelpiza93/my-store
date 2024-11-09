package com.mapiz.mystore.purchaseorder.infrastructure.api;

import static com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.purchaseorder.application.usecase.ReceiveOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class ReceivePurchaseOrderController {

  private final ReceiveOrderUseCase receiveOrderUseCase;

  @PostMapping("/{id}/receive")
  public @ResponseBody ResponseEntity<Void> receive(@PathVariable Integer id) {
    receiveOrderUseCase.accept(id);
    return ResponseEntity.accepted().build();
  }
}
