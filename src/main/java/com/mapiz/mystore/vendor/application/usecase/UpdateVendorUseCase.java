package com.mapiz.mystore.vendor.application.usecase;

import com.mapiz.mystore.vendor.application.command.UpdateVendorCommand;
import com.mapiz.mystore.vendor.domain.Vendor;
import java.util.function.Function;

@FunctionalInterface
public interface UpdateVendorUseCase extends Function<UpdateVendorCommand, Vendor> {}
