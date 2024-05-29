package com.mapiz.mystore.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Builder
@Data
@Entity
public class PurchaseOrder {

    @Id
    @SequenceGenerator(name = "purchase_order_sequence", sequenceName = "purchase_order_sequence", allocationSize = 1)
    @GeneratedValue(generator = "purchase_order_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;

    private int quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "estimated_delivery_date")
    private Date estimatedDeliveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
