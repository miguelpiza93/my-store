package com.mapiz.mystore.vendor.application.dto;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkProductsToVendorRequest {
    private Map<Integer, Double> products;
}
