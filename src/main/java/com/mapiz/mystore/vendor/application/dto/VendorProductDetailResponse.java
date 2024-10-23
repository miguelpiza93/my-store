package com.mapiz.mystore.vendor.application.dto;

import com.mapiz.mystore.unit.application.dto.UnitResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class VendorProductDetailResponse {
  private Integer id;
  private String name;
  private String vendorName;
  private String description;
  private String referenceUnit;
  private List<VendorProductPriceDetail> salePrices;
  private List<UnitResponse> units;
}
