package com.mapiz.mystore.purchaseorder.application.mapper;

import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineResponse;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface PurchaseOrderLineMapper {

  PurchaseOrderLineMapper INSTANCE = Mappers.getMapper(PurchaseOrderLineMapper.class);

  @Mapping(source = "vendorProduct.product", target = "product")
  PurchaseOrderLineResponse entityToModel(PurchaseOrderLine save);
}
