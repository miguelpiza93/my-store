package com.mapiz.mystore.product.infrastructure.persistence.mapper;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract Product toDomain(ProductEntity productEntity);
    public abstract ProductEntity toEntity(Product product);
}
