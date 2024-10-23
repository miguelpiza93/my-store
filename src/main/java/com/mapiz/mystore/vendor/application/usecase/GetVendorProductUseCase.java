package com.mapiz.mystore.vendor.application.usecase;

import com.mapiz.mystore.vendor.application.dto.VendorProductDetailResponse;
import java.util.function.BiFunction;

@FunctionalInterface
public interface GetVendorProductUseCase
    extends BiFunction<Integer, Integer, VendorProductDetailResponse> {}
