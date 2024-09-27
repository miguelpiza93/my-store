package com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class PurchaseOrderLineMapper {

  public static final PurchaseOrderLineMapper INSTANCE =
      Mappers.getMapper(PurchaseOrderLineMapper.class);

  public abstract PurchaseOrderLineEntity modelToEntity(PurchaseOrderLine purchaseOrderLine);

  public abstract PurchaseOrderLine entityToModel(PurchaseOrderLineEntity save);
}
