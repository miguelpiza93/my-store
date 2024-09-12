package com.mapiz.mystore.purchaseorder.infrastructure.api;

import com.mapiz.mystore.purchaseorder.application.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.purchaseorder.application.usecase.CreatePurchaseOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/v1/purchase-orders")
@RequiredArgsConstructor
public class CreatePurchaseOrderController {

    private final CreatePurchaseOrderUseCase createPurchaseOrderUseCase;

    @PostMapping
    public @ResponseBody ResponseEntity<PurchaseOrderEntity> create(@RequestBody CreatePurchaseOrderRequest request) {
        var command = PurchaseOrderMapper.INSTANCE.createPurchaseOrderRequestToCommand(request);
        createPurchaseOrderUseCase.apply(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
