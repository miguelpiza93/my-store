package com.mapiz.mystore.service;

import com.mapiz.mystore.dto.AddProductToSupplierRequest;
import com.mapiz.mystore.entity.Product;
import com.mapiz.mystore.entity.Supplier;
import com.mapiz.mystore.entity.SupplierProduct;
import com.mapiz.mystore.exception.SupplierNotFoundException;
import com.mapiz.mystore.repository.IProductRepository;
import com.mapiz.mystore.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;

    @Autowired
    private IProductRepository productRepository;

    public Iterable<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier addProductToSupplier(AddProductToSupplierRequest request) throws SupplierNotFoundException {
        Supplier supplier = supplierRepository.findById(request.getSupplierId()).orElseThrow(() -> SupplierNotFoundException.builder().build());
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> SupplierNotFoundException.builder().build());
        SupplierProduct supplierProduct = SupplierProduct.builder()
                .product(product)
                .supplier(supplier)
                .price(request.getPrice())
                .build();
        supplier.getProducts().add(supplierProduct);
        return supplierRepository.save(supplier);
    }
}
