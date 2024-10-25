package com.mapiz.mystore.vendor.application.mapper;

import com.mapiz.mystore.vendor.application.command.CreateVendorCommand;
import com.mapiz.mystore.vendor.application.command.UpdateVendorCommand;
import com.mapiz.mystore.vendor.application.dto.UpdateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface VendorMapper {

  VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

  @Mapping(target = "id", ignore = true)
  Vendor commandToModel(CreateVendorCommand command);

  @Mapping(target = "id", ignore = true)
  UpdateVendorCommand updateRequestToCommand(UpdateVendorRequest request);

  VendorResponse modelToVendorResponse(Vendor result);
}
