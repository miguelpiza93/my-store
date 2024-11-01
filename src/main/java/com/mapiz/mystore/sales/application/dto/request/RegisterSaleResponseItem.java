package com.mapiz.mystore.sales.application.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterSaleResponseItem {
  private Integer id;
  private Integer unitId;
  private Integer vendorProductId;
  private BigDecimal quantity;
  private BigDecimal unitPrice;
  private BigDecimal total;
  private BigDecimal cost;
}
