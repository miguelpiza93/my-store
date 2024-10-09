package com.mapiz.mystore.sales.infrastructure.persistence.repository;

import com.mapiz.mystore.sales.infrastructure.persistence.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSaleRepository extends JpaRepository<SaleEntity, Integer> {}
