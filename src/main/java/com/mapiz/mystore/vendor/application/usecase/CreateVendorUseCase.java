package com.mapiz.mystore.vendor.application.usecase;

import com.mapiz.mystore.vendor.application.command.CreateVendorCommand;
import com.mapiz.mystore.vendor.domain.Vendor;

import java.util.function.Function;

@FunctionalInterface
public interface CreateVendorUseCase extends Function<CreateVendorCommand, Vendor> {
}
