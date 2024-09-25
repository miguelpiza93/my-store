package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.usecase.LinkProductsToVendorUseCase;
import com.mapiz.mystore.vendor.application.command.LinkProductsToVendorCommand;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class LinkProductsToVendorUseCaseImpl implements LinkProductsToVendorUseCase {

    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final ProductVendorRepository productVendorRepository;

    @Override
    public List<ProductVendor> apply(LinkProductsToVendorCommand command) {
        final var vendorId = command.getVendorId();
        var vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found"));

        List<Product> products = productRepository.findAllById(command.getProducts().keySet());
        removeExistingProductsForVendor(vendorId);

        List<ProductVendor> supplierProducts = buildSupplierProducts(vendor, products, command.getProducts());
        return productVendorRepository.saveAll(supplierProducts);
    }

    private List<ProductVendor> buildSupplierProducts(Vendor vendor, List<Product> products, Map<Integer, Double> prices) {
        return products.stream()
                .map(product -> ProductVendor.builder()
                        .product(product)
                        .vendor(vendor)
                        .price(prices.get(product.getId()))
                        .build()
                ).toList();
    }

    private void removeExistingProductsForVendor(Integer vendorId) {
        productVendorRepository.deleteAll(productVendorRepository.findBySupplierId(vendorId));
    }
}
