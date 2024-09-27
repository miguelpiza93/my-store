package com.mapiz.mystore.vendor.application.dto;

import java.util.Map;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkProductsToVendorRequest {
  private Map<Integer, Double> products;
}
