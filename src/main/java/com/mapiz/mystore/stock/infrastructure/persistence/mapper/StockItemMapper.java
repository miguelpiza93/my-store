package com.mapiz.mystore.stock.infrastructure.persistence.mapper;

import com.mapiz.mystore.purchaseorder.infrastructure.persistence.mapper.PurchaseOrderLineMapper;
import com.mapiz.mystore.stock.domain.StockItem;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {PurchaseOrderLineMapper.class})
public interface StockItemMapper {

  StockItemEntity modelToEntity(StockItem stockItem);

  StockItem entityToModel(StockItemEntity entity);
}
