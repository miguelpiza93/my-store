package com.mapiz.mystore.vendor.application.usecase;

import com.mapiz.mystore.vendor.domain.VendorProduct;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface GetProductsVendorUseCase extends Function<Integer, List<VendorProduct>> {}
