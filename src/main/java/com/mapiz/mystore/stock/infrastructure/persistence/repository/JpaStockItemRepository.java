package com.mapiz.mystore.stock.infrastructure.persistence.repository;

import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStockItemRepository extends JpaRepository<StockItemEntity, Integer> {

  @Query(
      "SELECT s FROM stock_items s WHERE s.purchaseOrderLine.vendorProduct.id IN"
          + " (:vendorProductIds) AND s.quantity > 0 ORDER BY s.purchaseOrderLine.createdAt ASC")
  List<StockItemEntity> findByVendorProductIds(
      @Param("vendorProductIds") Collection<Integer> vendorProductIds);

  @Query("SELECT s FROM stock_items s WHERE s.quantity > 0")
  List<StockItemEntity> findAllAvailable();
}
