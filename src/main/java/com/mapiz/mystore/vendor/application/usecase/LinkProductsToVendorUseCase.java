package com.mapiz.mystore.vendor.application.usecase;

import com.mapiz.mystore.vendor.application.command.LinkProductsToVendorCommand;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface LinkProductsToVendorUseCase
    extends Function<LinkProductsToVendorCommand, List<VendorProduct>> {}
