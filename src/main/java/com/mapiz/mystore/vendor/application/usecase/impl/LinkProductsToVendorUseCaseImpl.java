package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import com.mapiz.mystore.vendor.application.command.LinkProductsToVendorCommand;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.vendor.application.usecase.LinkProductsToVendorUseCase;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.repository.VendorProductRepository;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkProductsToVendorUseCaseImpl implements LinkProductsToVendorUseCase {

  private final VendorRepository vendorRepository;
  private final ProductRepository productRepository;
  private final VendorProductRepository productVendorRepository;

  @Override
  public List<VendorProduct> apply(LinkProductsToVendorCommand command) {
    final var vendorId = command.getVendorId();
    var vendor =
        vendorRepository
            .findById(vendorId)
            .orElseThrow(() -> new VendorNotFoundException("Vendor not found"));

    List<Product> products = productRepository.findAllById(command.getProducts().keySet());
    removeExistingProductsForVendor(vendorId);

    List<VendorProduct> supplierProducts =
        buildSupplierProducts(vendor, products, command.getProducts());
    return productVendorRepository.saveAll(supplierProducts);
  }

  private List<VendorProduct> buildSupplierProducts(
      Vendor vendor, List<Product> products, Map<Integer, BigDecimal> prices) {
    return products.stream()
        .map(
            product ->
                VendorProduct.builder()
                    .product(product)
                    .vendor(vendor)
                    .price(prices.get(product.getId()))
                    .build())
        .toList();
  }

  private void removeExistingProductsForVendor(Integer vendorId) {
    productVendorRepository.deleteAll(productVendorRepository.findBySupplierId(vendorId));
  }
}
