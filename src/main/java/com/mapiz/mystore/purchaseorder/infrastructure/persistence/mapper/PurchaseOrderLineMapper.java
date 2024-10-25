package com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper;

import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PurchaseOrderLineMapper {

  @Autowired protected VendorProductMapper vendorProductMapper;

  @Mapping(target = "vendorProduct", ignore = true)
  public abstract PurchaseOrderLineEntity modelToEntity(PurchaseOrderLine purchaseOrderLine);

  @Mappings({
    @Mapping(target = "purchaseOrder", ignore = true),
    @Mapping(target = "vendorProduct", ignore = true),
  })
  public abstract PurchaseOrderLine entityToModel(PurchaseOrderLineEntity save);

  @AfterMapping
  public void postMappingModel(
      PurchaseOrderLineEntity source, @MappingTarget PurchaseOrderLine target) {
    VendorProduct vendorProduct = vendorProductMapper.entityToModel(source.getVendorProduct());
    target.setVendorProduct(vendorProduct);
  }

  @AfterMapping
  public void postMappingEntity(
      PurchaseOrderLine source, @MappingTarget PurchaseOrderLineEntity target) {
    VendorProductEntity vendorProduct =
        vendorProductMapper.modelToEntity(source.getVendorProduct());
    target.setVendorProduct(vendorProduct);
  }
}
