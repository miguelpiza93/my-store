package com.mapiz.mystore.product.application.usecase.impl;

import com.mapiz.mystore.product.application.exception.ProductNotFoundException;
import com.mapiz.mystore.product.application.usecase.DeleteProductUseCase;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {

  private final ProductRepository productRepository;

  @Override
  public void accept(Integer productId) {
    productRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
    productRepository.deleteById(productId);
  }
}
