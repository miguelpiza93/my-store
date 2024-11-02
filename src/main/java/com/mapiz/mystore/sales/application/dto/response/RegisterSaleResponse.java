package com.mapiz.mystore.sales.application.dto.response;

import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterSaleResponse {
  private Integer id;
  private String status;
  private Instant createdAt;
  private List<RegisterSaleResponseItem> lines;

  public BigDecimal getTotal() {
    return lines.stream()
        .map(RegisterSaleResponseItem::getTotal)
        .reduce(BigDecimal.ZERO, BigDecimalUtils::add);
  }
}
