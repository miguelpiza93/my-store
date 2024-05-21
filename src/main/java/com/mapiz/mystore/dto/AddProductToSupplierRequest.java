package com.mapiz.mystore.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AddProductToSupplierRequest {
    private Map<Integer, Double> products;
    private Integer supplierId;
}
