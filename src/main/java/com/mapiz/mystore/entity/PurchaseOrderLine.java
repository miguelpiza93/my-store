package com.mapiz.mystore.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.Instant;

@Builder
@Data
@Entity
public class PurchaseOrderLine {

    @Id
    @SequenceGenerator(name = "purchase_order_line_sequence", sequenceName = "purchase_order_line_sequence", allocationSize = 1)
    @GeneratedValue(generator = "purchase_order_line_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;

    private int quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;
}
