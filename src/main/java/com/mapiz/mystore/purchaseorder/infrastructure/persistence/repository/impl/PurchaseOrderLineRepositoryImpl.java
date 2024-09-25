package com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.impl;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderLineMapper;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PurchaseOrderLineRepositoryImpl implements PurchaseOrderLineRepository {

    private final JpaPurchaseOrderLineRepository jpaRepository;

    @Override
    public List<PurchaseOrderLine> save(List<PurchaseOrderLine> lines) {
        var entities = lines.stream().map(PurchaseOrderLineMapper.INSTANCE::modelToEntity).toList();
        return jpaRepository.saveAll(entities).stream().map(PurchaseOrderLineMapper.INSTANCE::entityToModel).toList();
    }
}
