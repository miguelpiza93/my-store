package com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
    componentModel = "spring",
    uses = {PurchaseOrderLineMapper.class, VendorMapper.class})
public interface PurchaseOrderMapper {

  PurchaseOrder entityToModel(PurchaseOrderEntity entity);

  @Mappings({@Mapping(target = "purchaseOrderLines", ignore = true)})
  PurchaseOrderEntity modelToEntity(PurchaseOrder purchaseOrder);
}
