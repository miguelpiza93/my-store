package com.mapiz.mystore.vendor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Vendor {
  private Integer id;
  private String name;
}
