package com.mapiz.mystore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Supplier {

    @Id
    @SequenceGenerator(name = "supplier_sequence", sequenceName = "supplier_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_sequence")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<SupplierProduct> products;
}
