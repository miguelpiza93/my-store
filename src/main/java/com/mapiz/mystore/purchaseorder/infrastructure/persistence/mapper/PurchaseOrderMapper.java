package com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PurchaseOrderLineMapper.class})
public abstract class PurchaseOrderMapper {

  public static final PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

  public abstract PurchaseOrder entityToModel(
      PurchaseOrderEntity entity, @Context CycleAvoidingMappingContext context);

  public abstract PurchaseOrderEntity modelToEntity(
      PurchaseOrder purchaseOrder, @Context CycleAvoidingMappingContext context);
}
