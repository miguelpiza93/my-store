package com.mapiz.mystore.vendor.application.command;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LinkProductsToVendorCommand {

  private Map<Integer, Double> products;
  private Integer vendorId;
}
