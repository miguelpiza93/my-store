package com.mapiz.mystore.vendor.application.mapper;

import com.mapiz.mystore.vendor.application.command.CreateVendorCommand;
import com.mapiz.mystore.vendor.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class VendorMapper {

  public static final VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

  @Mapping(target = "id", ignore = true)
  public abstract Vendor commandToModel(CreateVendorCommand command);
}
