package com.mapiz.mystore.vendor.infrastructure.persistence.mapper;

import com.mapiz.mystore.vendor.application.command.CreateVendorCommand;
import com.mapiz.mystore.vendor.application.dto.CreateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.CreateVendorResponse;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorMapper {

  VendorEntity modelToEntity(Vendor model);

  Vendor entityToModel(VendorEntity entity);

  CreateVendorCommand requestToCommand(CreateVendorRequest request);

  CreateVendorResponse modelToCreateVendorResponse(Vendor result);
}
