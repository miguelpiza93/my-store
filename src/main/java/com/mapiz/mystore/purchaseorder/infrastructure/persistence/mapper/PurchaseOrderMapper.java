package com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public abstract class PurchaseOrderMapper {

    public static final PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    public abstract PurchaseOrder entityToModel(PurchaseOrderEntity entity);

    public abstract PurchaseOrderEntity modelToEntity(PurchaseOrder purchaseOrder);
}
