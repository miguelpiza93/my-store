package com.mapiz.mystore.purchaseorder.application.mapper;

import com.mapiz.mystore.product.application.mapper.ProductMapper;
import com.mapiz.mystore.purchaseorder.application.command.CreatePurchaseOrderCommand;
import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class, PurchaseOrderLineMapper.class})
public interface PurchaseOrderMapper {

  PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

  CreatePurchaseOrderCommand createPurchaseOrderRequestToCommand(
      CreatePurchaseOrderRequest request);

  @Mapping(source = "vendor.name", target = "supplierName")
  PurchaseOrderResponse modelToCreatePurchaseOrderResponse(PurchaseOrder result);
}
