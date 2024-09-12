package com.mapiz.mystore.purchaseorder.application.mapper;

import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.usecase.command.CreatePurchaseOrderCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public abstract class PurchaseOrderMapper {

    public static final PurchaseOrderMapper INSTANCE = Mappers.getMapper(PurchaseOrderMapper.class);

    public abstract CreatePurchaseOrderCommand createPurchaseOrderRequestToCommand(CreatePurchaseOrderRequest request);

}
