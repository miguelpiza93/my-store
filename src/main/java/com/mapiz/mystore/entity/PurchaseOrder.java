package com.mapiz.mystore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @SequenceGenerator(name = "purchase_order_sequence", sequenceName = "purchase_order_sequence", allocationSize = 1)
    @GeneratedValue(generator = "purchase_order_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "estimated_delivery_date")
    private LocalDate estimatedDeliveryDate;

    @ManyToOne
    @Fetch(value = FetchMode.JOIN)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PurchaseOrderLine> purchaseOrderLines;
}
