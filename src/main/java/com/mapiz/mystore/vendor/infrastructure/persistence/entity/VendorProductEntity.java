package com.mapiz.mystore.vendor.infrastructure.persistence.entity;

import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vendor_products")
public class VendorProductEntity {

  @Id
  @SequenceGenerator(
      name = "vendor_product_sequence",
      sequenceName = "vendor_product_sequence",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_product_sequence")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vendor_id", nullable = false)
  private VendorEntity vendor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  private BigDecimal price;

  @OneToMany(mappedBy = "vendorProduct", fetch = FetchType.LAZY)
  private List<VendorProductUnitVariantEntity> salePrices;
}
