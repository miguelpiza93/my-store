package com.mapiz.mystore.product.domain.repository;

import com.mapiz.mystore.product.domain.Product;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository {

  Optional<Product> findById(Integer id);

  List<Product> findAll();

  Product save(Product product);

  void deleteById(Integer id);

  List<Product> findAllById(Set<Integer> integers);
}
