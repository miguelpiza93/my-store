package com.mapiz.mystore.product.infrastructure.persistence.repository;

import com.mapiz.mystore.product.infrastructure.persistence.ProductPriceEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductPriceRepository extends JpaRepository<ProductPriceEntity, Integer> {
  Optional<ProductPriceEntity> findByProductIdAndUnitId(Integer productId, Integer unitId);

  List<ProductPriceEntity> findByProductId(Integer productId);
}
