package com.mapiz.mystore.product.application.mapper;

import com.mapiz.mystore.product.application.command.CreateProductCommand;
import com.mapiz.mystore.product.application.dto.CreateProductRequest;
import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ProductMapper {

  public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  public abstract CreateProductCommand requestToCommand(CreateProductRequest request);

  public abstract Product commandToModel(CreateProductCommand command);

  public abstract ProductResponse modelToResponse(Product product);
}
