package com.mapiz.mystore.product.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductRepository;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

  private final JpaProductRepository jpaRepository;

  @Override
  public Optional<Product> findById(Integer id) {
    return jpaRepository.findById(id).map(getMapper());
  }

  @Override
  public List<Product> findAll() {
    return jpaRepository.findAll().stream().map(getMapper()).collect(Collectors.toList());
  }

  @Override
  public Product save(Product product) {
    ProductEntity entity = ProductMapper.INSTANCE.toEntity(product);
    return ProductMapper.INSTANCE.toDomain(
        jpaRepository.save(entity), new CycleAvoidingMappingContext());
  }

  @Override
  public void deleteById(Integer id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public List<Product> findAllById(Set<Integer> ids) {
    return jpaRepository.findAllById(ids).stream().map(getMapper()).toList();
  }

  private Function<ProductEntity, Product> getMapper() {
    return product -> ProductMapper.INSTANCE.toDomain(product, new CycleAvoidingMappingContext());
  }
}
