package com.mapiz.mystore.purchaseorder.application.dto;

import com.mapiz.mystore.product.application.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderLineResponse {
  private Integer id;
  private ProductResponse product;
  private Integer quantity;
  private Double unitPrice;
}
