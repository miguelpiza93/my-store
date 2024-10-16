package com.mapiz.mystore.product.domain.repository;

import com.mapiz.mystore.product.domain.ProductPrice;
import java.util.Optional;

public interface ProductPriceRepository {

  ProductPrice save(ProductPrice productPrice);

  Optional<ProductPrice> findByProductIdAndUnitId(Integer productId, Integer unitId);
}
