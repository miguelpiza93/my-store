package com.mapiz.mystore.sales.infrastructure.persistence.entity;

import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "sales")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleEntity {

  @Id
  @SequenceGenerator(name = "sales_sequence", sequenceName = "sales_sequence", allocationSize = 1)
  @GeneratedValue(generator = "sales_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  private BigDecimal quantity;

  @OneToOne
  @JoinColumn(name = "unit_id", nullable = false)
  private UnitEntity unit;

  private BigDecimal price;

  private BigDecimal total;

  private BigDecimal cost;

  @Column(name = "created_at")
  private Instant createdAt;
}
