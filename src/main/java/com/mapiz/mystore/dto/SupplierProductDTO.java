package com.mapiz.mystore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierProductDTO {
    private String name;
    private String description;
    private double price;
}
