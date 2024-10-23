package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.product.application.exception.ProductNotFoundException;
import com.mapiz.mystore.vendor.application.dto.VendorProductDetailResponse;
import com.mapiz.mystore.vendor.application.mapper.VendorProductMapper;
import com.mapiz.mystore.vendor.application.usecase.GetVendorProductUseCase;
import com.mapiz.mystore.vendor.domain.repository.VendorProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetVendorProductUseCaseImpl implements GetVendorProductUseCase {

  private final VendorProductRepository vendorProductRepository;

  @Override
  public VendorProductDetailResponse apply(Integer vendorId, Integer productId) {
    var vendorProduct =
        vendorProductRepository
            .findByVendorIdAndProductId(vendorId, productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
    return VendorProductMapper.INSTANCE.modelToDetailResponse(vendorProduct);
  }
}
