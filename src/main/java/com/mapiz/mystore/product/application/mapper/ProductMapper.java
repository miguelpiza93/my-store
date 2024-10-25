package com.mapiz.mystore.product.application.mapper;

import com.mapiz.mystore.product.application.command.CreateProductCommand;
import com.mapiz.mystore.product.application.dto.CreateProductRequest;
import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.vendor.application.mapper.VendorProductUnitVariantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = VendorProductUnitVariantMapper.class)
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  CreateProductCommand requestToCommand(CreateProductRequest request);

  @Mapping(source = "referenceUnitId", target = "referenceUnit.id")
  @Mapping(target = "id", ignore = true)
  Product commandToModel(CreateProductCommand command);

  ProductResponse modelToResponse(Product product);
}
