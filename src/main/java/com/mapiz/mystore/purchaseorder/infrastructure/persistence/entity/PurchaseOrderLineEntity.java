package com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity;

import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderLineEntity {

    @Id
    @SequenceGenerator(name = "purchase_order_line_sequence", sequenceName = "purchase_order_line_sequence", allocationSize = 1)
    @GeneratedValue(generator = "purchase_order_line_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;

    private int quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrderEntity purchaseOrder;
}
