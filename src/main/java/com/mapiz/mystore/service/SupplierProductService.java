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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Data
@Service
public class SupplierProductService {

    private final ISupplierRepository supplierRepository;
    private final IProductRepository productRepository;
    private final ISupplierProductRepository supplierProductRepository;

    @Transactional(rollbackFor = Exception.class)
    public Iterable<SupplierProduct> addProductsToSupplier(AddProductToSupplierRequest request) throws SupplierNotFoundException, ProductNotFoundException {
        final Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> SupplierNotFoundException.builder().build());

        Iterable<Product> products = productRepository.findAllById(request.getProducts().keySet());
        supplierProductRepository.deleteAll(supplierProductRepository.findBySupplierId(request.getSupplierId()));

        List<SupplierProduct> supplierProducts = StreamSupport.stream(
                        products.spliterator(), false)
                .map(product -> SupplierProduct.builder()
                        .product(product)
                        .supplier(supplier)
                        .price(request.getProducts().get(product.getId()))
                        .build()
                ).toList();
        return supplierProductRepository.saveAll(supplierProducts);
    }
}
