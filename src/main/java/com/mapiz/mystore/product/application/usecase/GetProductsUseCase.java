package com.mapiz.mystore.product.application.usecase;

import com.mapiz.mystore.product.domain.Product;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetProductsUseCase extends Supplier<List<Product>> {
}
