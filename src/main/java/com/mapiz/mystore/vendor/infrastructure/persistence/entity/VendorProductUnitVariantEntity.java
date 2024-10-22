package com.mapiz.mystore.vendor.infrastructure.persistence.entity;

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
@Entity(name = "vendor_product_unit_variants")
public class VendorProductUnitVariantEntity {

  @Id
  @SequenceGenerator(
      name = "vendor_product_unit_variants_sequence",
      sequenceName = "vendor_product_unit_variants_sequence",
      allocationSize = 1)
  @GeneratedValue(
      generator = "vendor_product_unit_variants_sequence",
      strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "vendor_product_id", nullable = false)
  private VendorProductEntity vendorProduct;

  @ManyToOne
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "unit_id", nullable = false)
  private UnitEntity unit;

  @Column(name = "sale_price")
  private BigDecimal salePrice;

  @Override
  public String toString() {
    return "ProductPriceEntity{"
        + "id="
        + id
        + ", VendorProduct="
        + vendorProduct.getId()
        + ", unit="
        + unit
        + ", salePrice="
        + salePrice
        + '}';
  }
}
