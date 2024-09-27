package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.usecase.GetProductsVendorUseCase;
import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductsVendorUseCaseImpl implements GetProductsVendorUseCase {

  private final ProductVendorRepository productVendorRepository;

  @Override
  public List<ProductVendor> apply(Integer vendorId) {
    return productVendorRepository.findBySupplierId(vendorId);
  }
}
