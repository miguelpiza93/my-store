package com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.infrastructure.persistence.mapper.ProductMapper;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public abstract class PurchaseOrderLineMapper {

  public static final PurchaseOrderLineMapper INSTANCE =
      Mappers.getMapper(PurchaseOrderLineMapper.class);

  public abstract PurchaseOrderLineEntity modelToEntity(PurchaseOrderLine purchaseOrderLine);

  public abstract PurchaseOrderLine entityToModel(
      PurchaseOrderLineEntity save, @Context CycleAvoidingMappingContext context);
}
