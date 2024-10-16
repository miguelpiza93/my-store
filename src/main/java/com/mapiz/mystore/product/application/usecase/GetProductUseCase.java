package com.mapiz.mystore.product.application.usecase;

import com.mapiz.mystore.product.application.dto.ProductDetailResponse;
import java.util.function.Function;

@FunctionalInterface
public interface GetProductUseCase extends Function<Integer, ProductDetailResponse> {}
