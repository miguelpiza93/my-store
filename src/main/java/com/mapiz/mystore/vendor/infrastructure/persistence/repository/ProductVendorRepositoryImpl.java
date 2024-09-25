package com.mapiz.mystore.vendor.infrastructure.persistence.repository;

import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.ProductVendorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductVendorRepositoryImpl implements ProductVendorRepository {

    private final JpaProductVendorRepository jpaRepository;

    @Override
    public List<ProductVendor> findBySupplierId(Integer vendorId) {
        return jpaRepository.findByVendorId(vendorId).stream()
                .map(ProductVendorMapper.INSTANCE::entityToModel)
                .toList();
    }

    @Override
    public List<ProductVendor> findBySupplierIdAndProductIdIn(Integer supplierId, List<Integer> productIds) {
        return jpaRepository.findByVendorIdAndProductIdIn(supplierId, productIds).stream()
                .map(ProductVendorMapper.INSTANCE::entityToModel).toList();
    }

    @Override
    public void deleteAll(List<ProductVendor> bySupplierId) {
        jpaRepository.deleteAllById(bySupplierId.stream().map(ProductVendor::getId).toList());
    }

    @Override
    public List<ProductVendor> saveAll(List<ProductVendor> list) {
        var toSave = list.stream().map(ProductVendorMapper.INSTANCE::modelToEntity).toList();
        return jpaRepository.saveAll(toSave).stream().map(ProductVendorMapper.INSTANCE::entityToModel).toList();
    }
}
