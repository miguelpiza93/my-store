package com.mapiz.mystore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private int id;
    private String name;
    private List<SupplierProductDTO> products;
}
