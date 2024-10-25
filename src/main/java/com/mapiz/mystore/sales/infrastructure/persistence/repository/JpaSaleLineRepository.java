package com.mapiz.mystore.sales.infrastructure.persistence.repository;

import com.mapiz.mystore.sales.infrastructure.persistence.entity.SaleLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSaleLineRepository extends JpaRepository<SaleLineEntity, Integer> {}
