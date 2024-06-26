package com.mapiz.mystore.controller;

import com.mapiz.mystore.dto.AddProductToSupplierRequest;
import com.mapiz.mystore.entity.SupplierProduct;
import com.mapiz.mystore.exception.BusinessException;
import com.mapiz.mystore.service.SupplierProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/supplier-products")
public class SupplierProductController {

    @Autowired
    private SupplierProductService supplierProductService;

    @PostMapping
    public @ResponseBody ResponseEntity<List<SupplierProduct>> addProductToSupplier(@RequestBody AddProductToSupplierRequest request) throws BusinessException {
        List<SupplierProduct> result = supplierProductService.addProductsToSupplier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
