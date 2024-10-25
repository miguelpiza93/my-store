package com.mapiz.mystore.sales.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Sale {

  private Integer id;

  private String status;

  private Instant createdAt;

  private List<SaleLine> lines;

  private BigDecimal total;
}
