package com.mapiz.mystore.purchaseorder.infrastructure.api;

import static com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant.PURCHASE_ORDER_BASE_PATH;

import com.mapiz.mystore.purchaseorder.application.command.UpdateUnitPricePurchaseOrderLineCommand;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.application.dto.UpdateUnitPricePurchaseOrderLineRequest;
import com.mapiz.mystore.purchaseorder.application.usecase.UpdateUnitPricePurchaseOrderLineUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PURCHASE_ORDER_BASE_PATH)
@RequiredArgsConstructor
public class UpdateUnitPricePurchaseOrderLineController {

  private final UpdateUnitPricePurchaseOrderLineUseCase useCase;

  @PatchMapping("{orderId}/lines/{lineId}")
  public @ResponseBody ResponseEntity<PurchaseOrderResponse> create(
      @PathVariable Integer orderId,
      @PathVariable Integer lineId,
      @RequestBody UpdateUnitPricePurchaseOrderLineRequest request) {
    var command =
        UpdateUnitPricePurchaseOrderLineCommand.builder()
            .purchaseOrderId(orderId)
            .purchaseOrderLineId(lineId)
            .unitPrice(request.getUnitPrice())
            .build();
    useCase.accept(command);
    return ResponseEntity.ok().build();
  }
}
