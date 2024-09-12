package com.mapiz.mystore.vendor.application.usecase;

import com.mapiz.mystore.vendor.domain.Vendor;

import java.util.function.Function;

@FunctionalInterface
public interface GetVendorByIdUseCase extends Function<Integer, Vendor> {
}
