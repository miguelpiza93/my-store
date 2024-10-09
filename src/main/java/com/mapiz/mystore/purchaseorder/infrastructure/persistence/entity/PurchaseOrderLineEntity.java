package com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity;

import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "purchase_order_lines")
public class PurchaseOrderLineEntity {

  @Id
  @SequenceGenerator(
      name = "purchase_order_line_sequence",
      sequenceName = "purchase_order_line_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "purchase_order_line_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  private int quantity;

  @Column(name = "unit_price")
  private double unitPrice;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "purchase_order_id", nullable = false)
  private PurchaseOrderEntity purchaseOrder;

  @Column(name = "created_at")
  private Instant createdAt;

  @Override
  public String toString() {
    return "PurchaseOrderLineEntity{"
        + "id="
        + id
        + ", quantity="
        + quantity
        + ", unitPrice="
        + unitPrice
        + ", product="
        + product
        + '}';
  }
}
