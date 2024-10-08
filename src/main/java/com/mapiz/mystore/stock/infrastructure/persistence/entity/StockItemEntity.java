package com.mapiz.mystore.stock.infrastructure.persistence.entity;

import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "stock_items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockItemEntity {

  @Id
  @SequenceGenerator(
      name = "stock_item_sequence",
      sequenceName = "stock_item_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "stock_item_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "purchase_order_line_id", nullable = false)
  private PurchaseOrderLineEntity purchaseOrderLine;

  private int quantity;
}
