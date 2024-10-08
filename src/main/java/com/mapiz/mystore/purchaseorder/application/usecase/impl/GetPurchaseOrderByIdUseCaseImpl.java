package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.exception.PurchaseOrderNotFoundException;
import com.mapiz.mystore.purchaseorder.application.usecase.GetPurchaseOrderByIdUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPurchaseOrderByIdUseCaseImpl implements GetPurchaseOrderByIdUseCase {

  private final PurchaseOrderRepository purchaseOrderRepository;

  @Override
  public PurchaseOrder apply(Integer purchaseOrderId) {
    return purchaseOrderRepository
        .findById(purchaseOrderId)
        .orElseThrow(() -> new PurchaseOrderNotFoundException(purchaseOrderId));
  }
}
