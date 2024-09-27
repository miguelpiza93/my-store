package com.mapiz.mystore.vendor.application.usecase;

import com.mapiz.mystore.vendor.domain.Vendor;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetVendorsUseCase extends Supplier<List<Vendor>> {}
