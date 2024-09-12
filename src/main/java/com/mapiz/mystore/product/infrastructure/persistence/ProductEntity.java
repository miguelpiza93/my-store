package com.mapiz.mystore.product.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Data
@Entity
@Builder
public class ProductEntity {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(generator = "product_sequence", strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    private String description;

}
