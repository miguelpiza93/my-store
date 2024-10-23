package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.usecase.GetProductsVendorUseCase;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.repository.VendorProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductsVendorUseCaseImpl implements GetProductsVendorUseCase {

  private final VendorProductRepository productVendorRepository;

  @Override
  public List<VendorProduct> apply(Integer vendorId) {
    return productVendorRepository.findBySupplierId(vendorId);
  }
}
