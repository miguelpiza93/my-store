package com.mapiz.mystore.sales.application.mapper;

import com.mapiz.mystore.sales.application.command.RegisterSaleCommand;
import com.mapiz.mystore.sales.application.command.RegisterSaleCommandItem;
import com.mapiz.mystore.sales.application.dto.request.RegisterSaleRequest;
import com.mapiz.mystore.sales.domain.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SaleMapper {

  public static final SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

  public abstract RegisterSaleCommand requestToCommand(RegisterSaleRequest request);

  @Mapping(target = "product.id", source = "productId")
  @Mapping(target = "unit.id", source = "unitId")
  @Mapping(target = "total", ignore = true)
  @Mapping(target = "cost", ignore = true)
  @Mapping(target = "price", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  public abstract Sale commandItemToModel(RegisterSaleCommandItem registerSaleCommandItem);
}
