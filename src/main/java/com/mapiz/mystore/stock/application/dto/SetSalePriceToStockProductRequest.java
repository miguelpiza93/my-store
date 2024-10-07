package com.mapiz.mystore.stock.application.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SetSalePriceToStockProductRequest {

  @Positive private int salePrice;
}
