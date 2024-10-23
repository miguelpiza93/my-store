package com.mapiz.mystore.sales.infrastructure.persistence.entity;

import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductUnitVariantEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "sale_lines")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleLineEntity {

  @Id
  @SequenceGenerator(
      name = "sale_lines_sequence",
      sequenceName = "sale_lines_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "sale_lines_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "sale_id", nullable = false)
  private SaleEntity sale;

  @OneToOne
  @JoinColumn(name = "vendor_product_unit_variant_id", nullable = false)
  private VendorProductUnitVariantEntity vendorProductVariant;

  private BigDecimal quantity;

  @Column(name = "unit_price")
  private BigDecimal unitPrice;

  private BigDecimal total;

  private BigDecimal cost;
}
