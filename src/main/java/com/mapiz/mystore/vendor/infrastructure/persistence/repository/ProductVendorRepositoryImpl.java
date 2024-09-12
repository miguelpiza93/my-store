package com.mapiz.mystore.vendor.infrastructure.persistence.repository;

import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.ProductVendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.ProductVendorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductVendorRepositoryImpl implements ProductVendorRepository {

    private final JpaProductVendorRepository jpaRepository;

    @Override
    public List<ProductVendorEntity> findBySupplierId(Integer vendorId) {
        return jpaRepository.findByVendorId(vendorId);
    }

    @Override
    public void deleteAll(List<ProductVendorEntity> bySupplierId) {
        jpaRepository.deleteAll(bySupplierId);
    }

    @Override
    public List<ProductVendor> saveAll(List<ProductVendor> list) {
        var toSave = list.stream().map(ProductVendorMapper.INSTANCE::modelToEntity).toList();
        return jpaRepository.saveAll(toSave).stream().map(ProductVendorMapper.INSTANCE::entityToModel).toList();
    }
}
