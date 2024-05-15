package com.mapiz.mystore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(generator = "product_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "product")
    private Set<SupplierProduct> suppliers;
}
