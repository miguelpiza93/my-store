package com.mapiz.mystore.vendor.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vendors")
public class VendorEntity {

  @Id
  @SequenceGenerator(name = "vendor_sequence", sequenceName = "vendor_sequence", allocationSize = 1)
  @GeneratedValue(generator = "vendor_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String name;

  private String phone;
}
