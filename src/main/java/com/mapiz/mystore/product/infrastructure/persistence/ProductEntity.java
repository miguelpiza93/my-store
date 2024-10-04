package com.mapiz.mystore.product.infrastructure.persistence;

import com.mapiz.mystore.unit.infrastructure.persistence.entity.UnitEntity;
import jakarta.persistence.*;
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
@Entity(name = "products")
public class ProductEntity {

  @Id
  @SequenceGenerator(
      name = "product_sequence",
      sequenceName = "product_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "product_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String name;

  private String description;

  @ManyToOne
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "reference_unit", nullable = false)
  private UnitEntity referenceUnit;
}
