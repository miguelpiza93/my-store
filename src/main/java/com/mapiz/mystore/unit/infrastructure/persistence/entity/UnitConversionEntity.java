package com.mapiz.mystore.unit.infrastructure.persistence.entity;

import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "unit_conversions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitConversionEntity {
  @Id
  @SequenceGenerator(
      name = "unit_conversion_sequence",
      sequenceName = "unit_conversion_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "unit_conversion_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_unit_id", nullable = false)
  private UnitEntity fromUnit;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @ManyToOne
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "to_unit_id", nullable = false)
  private UnitEntity toUnit;

  @Column(name = "conversion_factor")
  private double conversionFactor;

  @Override
  public String toString() {
    return "ConversionEntity{"
        + "id="
        + id
        + ", toUnit="
        + toUnit.getId()
        + ", conversionFactor="
        + conversionFactor
        + '}';
  }
}
