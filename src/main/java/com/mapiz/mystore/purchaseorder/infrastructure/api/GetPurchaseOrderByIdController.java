package com.mapiz.mystore.purchaseorder.infrastructure.api;

import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrderByIdUseCase;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant.PURCHASE_ORDER_BASE_PATH;


@RestController
@RequestMapping(value = PURCHASE_ORDER_BASE_PATH)
@RequiredArgsConstructor
public class GetPurchaseOrderByIdController {

   private final GetPurchaseOrderByIdUseCase getPurchaseOrderByIdUseCase;

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<PurchaseOrderResponse> findById(@PathVariable Integer id) {
        var result =  getPurchaseOrderByIdUseCase.apply(id);
        return ResponseEntity.ok(PurchaseOrderMapper.INSTANCE.modelToCreatePurchaseOrderResponse(result));
    }
}