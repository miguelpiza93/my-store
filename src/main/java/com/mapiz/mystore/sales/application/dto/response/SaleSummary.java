package com.mapiz.mystore.sales.application.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class SaleSummary {
  private Integer id;
  private BigDecimal total;
  private String status;
  private Instant createdAt;
}
