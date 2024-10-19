package com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity;

import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
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

  private BigDecimal quantity;

  @Column(name = "unit_price")
  private double unitPrice;

  private BigDecimal total;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "vendor_product_id", nullable = false)
  private VendorProductEntity vendorProduct;

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
        + ", vendorProduct="
        + vendorProduct
        + '}';
  }
}
