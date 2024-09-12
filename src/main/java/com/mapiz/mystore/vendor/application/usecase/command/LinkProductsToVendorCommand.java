package com.mapiz.mystore.vendor.application.usecase.command;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class LinkProductsToVendorCommand {

    private Map<Integer, Double> products;
    private Integer vendorId;
}
