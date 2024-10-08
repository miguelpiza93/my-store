package com.mapiz.mystore.vendor.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVendorResponse {
  private Integer id;
  private String name;
  private String phone;
}
