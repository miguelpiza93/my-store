package com.mapiz.mystore.product.infrastructure.persistence;

import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product_prices")
public class ProductPriceEntity {

  @Id
  @SequenceGenerator(
      name = "product_prices_sequence",
      sequenceName = "product_prices_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "product_prices_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @ManyToOne
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "unit_id", nullable = false)
  private UnitEntity unit;

  @Column(name = "sale_price")
  private BigDecimal salePrice;
}
