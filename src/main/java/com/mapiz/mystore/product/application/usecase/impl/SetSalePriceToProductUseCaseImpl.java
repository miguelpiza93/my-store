package com.mapiz.mystore.product.application.usecase.impl;

import com.mapiz.mystore.product.application.exception.InvalidUnitForProductException;
import com.mapiz.mystore.product.application.exception.ProductNotFoundException;
import com.mapiz.mystore.product.application.usecase.SetSalePriceToProductUseCase;
import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.domain.ProductPrice;
import com.mapiz.mystore.product.domain.repository.ProductPriceRepository;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import com.mapiz.mystore.unit.domain.Unit;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetSalePriceToProductUseCaseImpl implements SetSalePriceToProductUseCase {

  private final ProductRepository productRepository;
  private final ProductPriceRepository productPriceRepository;

  @Override
  public void accept(SetSalePriceToStockProductCommand command) {
    var product =
        productRepository
            .findById(command.getProductId())
            .orElseThrow(() -> new ProductNotFoundException(command.getProductId()));

    var unitInUse =
        product
            .getDerivedUnit(command.getUnitId())
            .orElseThrow(
                () ->
                    new InvalidUnitForProductException(
                        command.getProductId(), command.getUnitId()));
    var productPrice = getProductPrice(command, product, unitInUse);
    productPriceRepository.save(productPrice);
  }

  private ProductPrice getProductPrice(
      SetSalePriceToStockProductCommand command, Product product, Unit unitInUse) {
    return productPriceRepository
        .findByProductIdAndUnitId(product.getId(), unitInUse.getId())
        .map(existingProductPrice -> updateSalePrice(existingProductPrice, command.getSalePrice()))
        .orElseGet(() -> buildNewProductPrice(product, unitInUse, command.getSalePrice()));
  }

  private ProductPrice updateSalePrice(ProductPrice productPrice, BigDecimal salePrice) {
    productPrice.setSalePrice(salePrice);
    return productPrice;
  }

  private ProductPrice buildNewProductPrice(Product product, Unit unitInUse, BigDecimal salePrice) {
    return ProductPrice.builder().product(product).unit(unitInUse).salePrice(salePrice).build();
  }
}
