package com.mapiz.mystore.sales.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
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

  @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
  private List<SaleLineEntity> lines;

  private String status;

  private BigDecimal total;

  @Column(name = "created_at")
  private Instant createdAt;
}
