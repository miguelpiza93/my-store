package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrdersUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPurchaseOrdersUseCaseImpl implements GetPurchaseOrdersUseCase {

  private final PurchaseOrderRepository purchaseOrderRepository;

  @Override
  public List<PurchaseOrder> get() {
    return purchaseOrderRepository.findAll();
  }
}
