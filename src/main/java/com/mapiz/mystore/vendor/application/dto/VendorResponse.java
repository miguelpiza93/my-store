package com.mapiz.mystore.vendor.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class VendorResponse {
    private Integer id;
    private String name;
}
