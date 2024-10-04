package com.mapiz.mystore.unit.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "units")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitEntity {
  @Id
  @SequenceGenerator(name = "unit_sequence", sequenceName = "unit_sequence", allocationSize = 1)
  @GeneratedValue(generator = "unit_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String name;
  private String symbol;
  private boolean isFractional;
  private boolean isBaseUnit;

  @OneToMany(mappedBy = "fromUnit", fetch = FetchType.EAGER)
  private List<ConversionEntity> unitConversions;
}
