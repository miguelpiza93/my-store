package com.mapiz.mystore.unit.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "conversions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversionEntity {
  @Id
  @SequenceGenerator(
      name = "conversion_sequence",
      sequenceName = "conversion_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "conversion_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "from_unit_id", nullable = false)
  private UnitEntity fromUnit;

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
