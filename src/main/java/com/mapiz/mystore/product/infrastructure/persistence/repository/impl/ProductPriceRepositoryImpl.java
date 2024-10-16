package com.mapiz.mystore.product.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.product.domain.ProductPrice;
import com.mapiz.mystore.product.domain.repository.ProductPriceRepository;
import com.mapiz.mystore.product.infrastructure.persistence.ProductPriceEntity;
import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductPriceMapper;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductPriceRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPriceRepositoryImpl implements ProductPriceRepository {

  private final JpaProductPriceRepository jpaProductPriceRepository;

  @Override
  public ProductPrice save(ProductPrice productPrice) {
    var productPriceEntity = ProductPriceMapper.INSTANCE.toEntity(productPrice);
    return ProductPriceMapper.INSTANCE.toDomain(
        jpaProductPriceRepository.save(productPriceEntity), new CycleAvoidingMappingContext());
  }

  @Override
  public Optional<ProductPrice> findByProductIdAndUnitId(Integer productId, Integer unitId) {
    return jpaProductPriceRepository.findByProductIdAndUnitId(productId, unitId).map(getMapper());
  }

  @Override
  public List<ProductPrice> findByProductId(Integer productId) {
    return jpaProductPriceRepository.findByProductId(productId).stream().map(getMapper()).toList();
  }

  private static Function<ProductPriceEntity, ProductPrice> getMapper() {
    return productPrice ->
        ProductPriceMapper.INSTANCE.toDomain(productPrice, new CycleAvoidingMappingContext());
  }
}
