package com.mapiz.mystore.stock.infrastructure.persistence.entity;

import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "stock_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockItemEntity {

  @Id
  @SequenceGenerator(
      name = "stock_item_sequence",
      sequenceName = "stock_item_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "stock_item_sequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  private int quantity;

  @Column(name = "sale_price")
  private double salePrice;
}
