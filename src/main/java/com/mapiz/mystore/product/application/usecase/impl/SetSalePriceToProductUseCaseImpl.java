package com.mapiz.mystore.product.application.usecase.impl;

import com.mapiz.mystore.product.application.exception.ProductNotFoundException;
import com.mapiz.mystore.product.application.usecase.SetSalePriceToProductUseCase;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetSalePriceToProductUseCaseImpl implements SetSalePriceToProductUseCase {

  private final ProductRepository productRepository;

  @Override
  public void accept(SetSalePriceToStockProductCommand command) {
    var product =
        productRepository
            .findById(command.getProductId())
            .orElseThrow(() -> new ProductNotFoundException(command.getProductId()));
    product.setSalePrice(command.getSalePrice());
    productRepository.save(product);
  }
}
