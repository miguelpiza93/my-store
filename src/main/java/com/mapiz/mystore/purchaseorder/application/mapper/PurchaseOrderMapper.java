package com.mapiz.mystore.purchaseorder.application.mapper;

import com.mapiz.mystore.product.application.mapper.ProductMapper;
import com.mapiz.mystore.purchaseorder.application.command.CreatePurchaseOrderCommand;
import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.vendor.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class})
public abstract class PurchaseOrderMapper {

  public static final PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

  public abstract CreatePurchaseOrderCommand createPurchaseOrderRequestToCommand(
      CreatePurchaseOrderRequest request);

  public String mapVendorToName(Vendor vendor) {
    return vendor.getName();
  }

  @Mapping(source = "vendor", target = "supplierName")
  public abstract PurchaseOrderResponse modelToCreatePurchaseOrderResponse(PurchaseOrder result);
}
