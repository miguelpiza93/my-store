package com.mapiz.mystore.product.application.usecase;

import com.mapiz.mystore.product.application.command.CreateProductCommand;
import com.mapiz.mystore.product.domain.Product;
import java.util.function.Function;

@FunctionalInterface
public interface CreateProductUseCase extends Function<CreateProductCommand, Product> {}
