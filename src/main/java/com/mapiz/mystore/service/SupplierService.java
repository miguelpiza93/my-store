package com.mapiz.mystore.service;

import com.mapiz.mystore.dto.SupplierDTO;
import com.mapiz.mystore.dto.SupplierProductDTO;
import com.mapiz.mystore.entity.Supplier;
import com.mapiz.mystore.repository.IProductRepository;
import com.mapiz.mystore.repository.ISupplierProductRepository;
import com.mapiz.mystore.repository.ISupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ISupplierProductRepository supplierProductRepository;

    public Iterable<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public SupplierDTO getById(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + supplierId));

        List<SupplierProductDTO> products = supplierProductRepository.findBySupplierId(supplierId).stream()
                .map(supplierProduct -> SupplierProductDTO.builder()
                        .id(supplierProduct.getProduct().getId())
                        .name(supplierProduct.getProduct().getName())
                        .description(supplierProduct.getProduct().getDescription())
                        .price(supplierProduct.getPrice())
                        .build()
                )
                .toList();
        return SupplierDTO.builder().name(supplier.getName()).products(products).build();
    }

    public Supplier updateSupplier(Integer id, Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));
        existingSupplier.setName(supplier.getName());
        return supplierRepository.save(existingSupplier);
    }
}
