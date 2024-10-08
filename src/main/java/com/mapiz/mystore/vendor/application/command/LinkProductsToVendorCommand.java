package com.mapiz.mystore.vendor.application.command;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LinkProductsToVendorCommand {

  private Map<Integer, BigDecimal> products;
  private Integer vendorId;
}
