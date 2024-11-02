package com.mapiz.mystore.sales.application.mapper;

import com.mapiz.mystore.sales.application.command.RegisterSaleCommand;
import com.mapiz.mystore.sales.application.command.RegisterSaleCommandItem;
import com.mapiz.mystore.sales.application.dto.request.RegisterSaleRequest;
import com.mapiz.mystore.sales.application.dto.response.RegisterSaleResponseItem;
import com.mapiz.mystore.sales.domain.SaleLine;
import com.mapiz.mystore.shared.BigDecimalMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BigDecimalMapper.class})
public interface SaleLineMapper {

  SaleLineMapper INSTANCE = Mappers.getMapper(SaleLineMapper.class);

  RegisterSaleCommand requestToCommand(RegisterSaleRequest request);

  @Mappings({
    @Mapping(target = "vendorProductVariant.vendorProduct.id", source = "vendorProductId"),
    @Mapping(target = "vendorProductVariant.unit.id", source = "unitId"),
    @Mapping(target = "total", ignore = true),
    @Mapping(target = "cost", ignore = true),
    @Mapping(target = "unitPrice", ignore = true),
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "sale", ignore = true),
    @Mapping(target = "quantity", source = "quantity")
  })
  SaleLine commandItemToModel(RegisterSaleCommandItem registerSaleCommandItem);

  @Mappings({
    @Mapping(target = "unitId", source = "vendorProductVariant.unit.id"),
    @Mapping(target = "vendorProductId", source = "vendorProductVariant.vendorProduct.id")
  })
  RegisterSaleResponseItem lineToItem(SaleLine line);
}
