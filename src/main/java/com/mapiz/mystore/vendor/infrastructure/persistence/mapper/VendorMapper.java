package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.vendor.application.dto.CreateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.CreateVendorResponse;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.application.command.CreateVendorCommand;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class VendorMapper {

    public static final VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    public abstract VendorEntity modelToEntity(Vendor model);

    public abstract Vendor entityToModel(VendorEntity entity);

    public abstract CreateVendorCommand requestToCommand(CreateVendorRequest request);

    public abstract CreateVendorResponse modelToCreateVendorResponse(Vendor result);

    public abstract VendorResponse modelToVendorResponse(Vendor result);
}
