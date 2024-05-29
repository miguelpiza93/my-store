package com.mapiz.mystore.controller;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.entity.PurchaseOrder;
import com.mapiz.mystore.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public @ResponseBody ResponseEntity<PurchaseOrder> create(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder =purchaseOrderService.create(purchaseOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrder);
    }
}
