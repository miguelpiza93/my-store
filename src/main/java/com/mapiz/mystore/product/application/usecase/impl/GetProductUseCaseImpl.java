package com.mapiz.mystore.product.application.usecase.impl;

import com.mapiz.mystore.product.application.dto.ProductDetailResponse;
import com.mapiz.mystore.product.application.exception.ProductNotFoundException;
import com.mapiz.mystore.product.application.mapper.ProductMapper;
import com.mapiz.mystore.product.application.usecase.GetProductUseCase;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductUseCaseImpl implements GetProductUseCase {

  private final ProductRepository productRepository;

  @Override
  public ProductDetailResponse apply(Integer productId) {
    var product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
    return ProductMapper.INSTANCE.modelToDetailResponse(product);
  }
}
