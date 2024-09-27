package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.exception.PurchaseOrderNotFoundException;
import com.mapiz.mystore.purchaseorder.application.usecase.ReceiveOrderUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderStatus;
import com.mapiz.mystore.purchaseorder.domain.event.OrderReceivedEvent;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiveOrderUseCaseImpl implements ReceiveOrderUseCase {

  private final PurchaseOrderRepository purchaseOrderRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void accept(Integer purchaseOrderId) {
    var purchaseOrder =
        purchaseOrderRepository
            .findById(purchaseOrderId)
            .orElseThrow(() -> new PurchaseOrderNotFoundException(purchaseOrderId));
    purchaseOrder.setStatus(PurchaseOrderStatus.RECEIVED);
    purchaseOrderRepository.save(purchaseOrder);
    eventPublisher.publishEvent(new OrderReceivedEvent(purchaseOrderId));
  }
}
