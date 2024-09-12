package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.vendor.application.dto.ProductVendorResponse;
import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.ProductVendorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ProductVendorMapper {

    public static final ProductVendorMapper INSTANCE = Mappers.getMapper(ProductVendorMapper.class);

    public abstract ProductVendor entityToModel(ProductVendorEntity entity);

    public abstract ProductVendorEntity modelToEntity(ProductVendor model);

    public abstract ProductVendorResponse modelToProductVendorResponse(ProductVendor productVendor);
}
