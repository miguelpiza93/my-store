package com.mapiz.mystore.product.infrastructure.persistence.repository;

import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Integer> {}
