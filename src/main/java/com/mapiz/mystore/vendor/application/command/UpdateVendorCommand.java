package com.mapiz.mystore.vendor.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVendorCommand {
  private Integer id;
  private String name;
  private String phone;
}
