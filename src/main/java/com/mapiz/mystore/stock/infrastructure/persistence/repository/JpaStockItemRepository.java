package com.mapiz.mystore.stock.infrastructure.persistence.repository;

import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStockItemRepository extends JpaRepository<StockItemEntity, Integer> {}
