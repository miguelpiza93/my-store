package com.mapiz.mystore.controller;

import com.mapiz.mystore.dto.SupplierDTO;
import com.mapiz.mystore.entity.Supplier;
import com.mapiz.mystore.exception.SupplierNotFoundException;
import com.mapiz.mystore.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public Iterable<Supplier> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @GetMapping(value = "/{id}")
    public SupplierDTO getSupplierById(@PathVariable Integer id) throws SupplierNotFoundException {
        return supplierService.getById(id);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier) {
        Supplier addedSupplier = supplierService.addSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSupplier);
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) throws SupplierNotFoundException {
        Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSupplier);
    }
}
