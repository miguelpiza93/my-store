package com.mapiz.mystore.purchaseorder.infrastructure.api;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrderByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/v1/purchase-orders")
@RequiredArgsConstructor
public class GetPurchaseOrderByIdController {

   private final GetPurchaseOrderByIdUseCase getPurchaseOrderByIdUseCase;

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<PurchaseOrder> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(getPurchaseOrderByIdUseCase.apply(id));
    }
}
