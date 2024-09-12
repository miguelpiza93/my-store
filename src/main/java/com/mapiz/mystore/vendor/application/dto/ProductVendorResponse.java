package com.mapiz.mystore.vendor.application.dto;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.vendor.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProductVendorResponse {
    private Integer id;
    private Vendor vendor;
    private Product product;
    private double price;
}
