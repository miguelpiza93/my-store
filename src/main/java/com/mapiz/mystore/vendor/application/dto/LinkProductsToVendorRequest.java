package com.mapiz.mystore.vendor.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class LinkProductsToVendorRequest {
    private Map<Integer, Double> products;
}
