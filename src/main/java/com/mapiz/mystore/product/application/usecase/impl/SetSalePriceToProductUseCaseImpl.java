package com.mapiz.mystore.product.application.usecase.impl;

import com.mapiz.mystore.product.application.exception.InvalidUnitForProductException;
import com.mapiz.mystore.product.application.exception.ProductNotFoundException;
import com.mapiz.mystore.product.application.usecase.SetSalePriceToProductUseCase;
import com.mapiz.mystore.product.domain.repository.UnitStockItemRepository;
import com.mapiz.mystore.stock.application.command.SetSalePriceToStockProductCommand;
import com.mapiz.mystore.unit.domain.Unit;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.VendorProductUnitVariant;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetSalePriceToProductUseCaseImpl implements SetSalePriceToProductUseCase {

  private final ProductVendorRepository productVendorRepository;
  private final UnitStockItemRepository unitStockItemRepository;

  @Override
  public void accept(SetSalePriceToStockProductCommand command) {
    var vendorProduct =
        productVendorRepository
            .findBySupplierIdAndProductId(command.getVendorId(), command.getProductId())
            .orElseThrow(() -> new ProductNotFoundException(command.getProductId()));

    var unitInUse =
        vendorProduct
            .getProduct()
            .getDerivedUnit(command.getUnitId())
            .orElseThrow(
                () ->
                    new InvalidUnitForProductException(
                        command.getProductId(), command.getUnitId()));
    var productPrice = getProductPrice(command, vendorProduct, unitInUse);
    unitStockItemRepository.save(productPrice);
  }

  private VendorProductUnitVariant getProductPrice(
      SetSalePriceToStockProductCommand command, VendorProduct vendorProduct, Unit unitInUse) {
    return unitStockItemRepository
        .findByVendorProductIdAndUnitId(vendorProduct.getId(), unitInUse.getId())
        .map(existingProductPrice -> updateSalePrice(existingProductPrice, command.getSalePrice()))
        .orElseGet(() -> buildNewProductPrice(vendorProduct, unitInUse, command.getSalePrice()));
  }

  private VendorProductUnitVariant updateSalePrice(
      VendorProductUnitVariant productPrice, BigDecimal salePrice) {
    productPrice.setSalePrice(salePrice);
    return productPrice;
  }

  private VendorProductUnitVariant buildNewProductPrice(
      VendorProduct vendorProduct, Unit unitInUse, BigDecimal salePrice) {
    return VendorProductUnitVariant.builder()
        .vendorProduct(vendorProduct)
        .unit(unitInUse)
        .salePrice(salePrice)
        .build();
  }
}
