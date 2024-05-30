package com.mapiz.mystore.controller;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.entity.PurchaseOrder;
import com.mapiz.mystore.mapper.PurchaseOrderMapper;
import com.mapiz.mystore.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public @ResponseBody ResponseEntity<PurchaseOrder> create(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        purchaseOrderService.create(purchaseOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<PurchaseOrderDTO>> findAll() {
        return ResponseEntity.ok(purchaseOrderService.findAll().stream().map(purchaseOrderMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<PurchaseOrderDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(purchaseOrderMapper.toFullDTO(purchaseOrderService.findById(id)));
    }
}
