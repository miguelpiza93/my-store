package com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper;

import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.application.usecase.command.CreatePurchaseOrderCommand;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.shared.CycleAvoidingMappingContext;
import com.mapiz.mystore.vendor.domain.Vendor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



@Mapper
public abstract class PurchaseOrderMapper {

    public static final PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    public abstract CreatePurchaseOrderCommand createPurchaseOrderRequestToCommand(CreatePurchaseOrderRequest request);

    @Mapping(source = "supplier", target = "vendor")
    public abstract PurchaseOrder entityToModel(PurchaseOrderEntity entity, @Context CycleAvoidingMappingContext context);

    @Mapping(source = "vendor", target = "supplier")
    public abstract PurchaseOrderEntity modelToEntity(PurchaseOrder purchaseOrder, @Context CycleAvoidingMappingContext context);

    public String mapVendorToName(Vendor vendor) {
        return vendor.getName();
    }

    @Mapping(source = "vendor", target = "supplierName")
    public abstract PurchaseOrderResponse modelToCreatePurchaseOrderResponse(PurchaseOrder result);
}
