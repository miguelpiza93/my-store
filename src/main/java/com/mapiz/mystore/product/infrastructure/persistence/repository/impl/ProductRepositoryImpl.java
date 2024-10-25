package com.mapiz.mystore.product.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductMapper productMapper;
  private final JpaProductRepository jpaRepository;

  @Override
  public Optional<Product> findById(Integer id) {
    var entity = jpaRepository.findById(id);
    return entity.map(productMapper::toDomain);
  }

  @Override
  public List<Product> findAll() {
    return jpaRepository.findAll().stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Product save(Product product) {
    ProductEntity entity = productMapper.toEntity(product);
    return productMapper.toDomain(jpaRepository.save(entity));
  }

  @Override
  public void deleteById(Integer id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public List<Product> findAllById(Set<Integer> ids) {
    return jpaRepository.findAllById(ids).stream().map(productMapper::toDomain).toList();
  }
}
