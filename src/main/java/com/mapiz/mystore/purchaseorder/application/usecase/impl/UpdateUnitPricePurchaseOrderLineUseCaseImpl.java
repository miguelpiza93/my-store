package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.command.UpdateUnitPricePurchaseOrderLineCommand;
import com.mapiz.mystore.purchaseorder.application.exception.PurchaseOrderLineNotFoundException;
import com.mapiz.mystore.purchaseorder.application.usecase.UpdateUnitPricePurchaseOrderLineUseCase;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUnitPricePurchaseOrderLineUseCaseImpl
    implements UpdateUnitPricePurchaseOrderLineUseCase {

  private final PurchaseOrderLineRepository purchaseOrderLineRepository;

  @Override
  public void accept(UpdateUnitPricePurchaseOrderLineCommand command) {
    var line =
        purchaseOrderLineRepository
            .findById(command.getPurchaseOrderLineId())
            .orElseThrow(
                () -> new PurchaseOrderLineNotFoundException(command.getPurchaseOrderLineId()));
    line.setUnitPrice(command.getUnitPrice());
    purchaseOrderLineRepository.save(line);
  }
}
