package com.mapiz.mystore.vendor.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Data
@Entity
@Builder
public class ProductVendorEntity {

    @Id
    @SequenceGenerator(name = "vendor_product_sequence", sequenceName = "vendor_product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_product_sequence")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private VendorEntity vendor;

    @Column(name = "product_id")
    private int productId;

    private double price;
}
