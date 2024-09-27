package com.mapiz.mystore.stock.application.listener;

import com.mapiz.mystore.purchaseorder.domain.event.OrderReceivedEvent;
import com.mapiz.mystore.stock.application.usecase.AddProductsToStockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReceivedEventListener {

  private final AddProductsToStockUseCase addProductsToStockUseCase;

  @EventListener
  public void handleOrderReceivedEvent(OrderReceivedEvent event) {
    addProductsToStockUseCase.accept(event.orderId());
  }
}
