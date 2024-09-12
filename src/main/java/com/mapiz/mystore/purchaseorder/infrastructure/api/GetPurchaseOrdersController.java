package com.mapiz.mystore.purchaseorder.infrastructure.api;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrdersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/purchase-orders")
@RequiredArgsConstructor
public class GetPurchaseOrdersController {

    private final GetPurchaseOrdersUseCase getPurchaseOrdersUseCase;

    @GetMapping
    public @ResponseBody ResponseEntity<List<PurchaseOrder>> findAll() {
        return ResponseEntity.ok(getPurchaseOrdersUseCase.get());
    }

}
