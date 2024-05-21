package com.mapiz.mystore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Supplier {

    @Id
    @SequenceGenerator(name = "product_supplier_sequence", sequenceName = "product_supplier_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_supplier_sequence")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SupplierProduct> products;
}
