package com.mapiz.mystore.vendor.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorMapper;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorRepositoryImpl implements VendorRepository {

  private final VendorMapper vendorMapper;
  private final JpaVendorRepository jpaRepository;

  @Override
  public Optional<Vendor> findById(Integer vendorId) {
    return jpaRepository.findById(vendorId).map(vendorMapper::entityToModel);
  }

  @Override
  public Vendor save(Vendor vendor) {
    var entity = jpaRepository.save(vendorMapper.modelToEntity(vendor));
    return vendorMapper.entityToModel(entity);
  }

  @Override
  public void deleteById(Integer id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public List<Vendor> findAll() {
    return jpaRepository.findAll().stream().map(vendorMapper::entityToModel).toList();
  }
}
