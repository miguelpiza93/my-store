package com.mapiz.mystore.service;

import com.mapiz.mystore.dto.AddProductToSupplierRequest;
import com.mapiz.mystore.entity.Product;
import com.mapiz.mystore.entity.Supplier;
import com.mapiz.mystore.entity.SupplierProduct;
import com.mapiz.mystore.exception.ProductNotFoundException;
import com.mapiz.mystore.exception.SupplierNotFoundException;
import com.mapiz.mystore.repository.IProductRepository;
import com.mapiz.mystore.repository.ISupplierProductRepository;
import com.mapiz.mystore.repository.ISupplierRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Data
@Service
public class SupplierProductService {

    private final ISupplierRepository supplierRepository;
    private final IProductRepository productRepository;
    private final ISupplierProductRepository supplierProductRepository;

    public SupplierProduct addProductSupplier(AddProductToSupplierRequest request) throws SupplierNotFoundException, ProductNotFoundException {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
            .orElseThrow(() -> SupplierNotFoundException.builder().build() );

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> ProductNotFoundException.builder().build() );

        SupplierProduct supplierProduct = SupplierProduct.builder()
            .product(product)
            .supplier(supplier)
            .price(request.getPrice())
            .build();

        return supplierProductRepository.save(supplierProduct);
    }
}
