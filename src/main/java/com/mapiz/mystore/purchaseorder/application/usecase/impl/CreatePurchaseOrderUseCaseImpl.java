package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.command.CreatePurchaseOrderCommand;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineRequest;
import com.mapiz.mystore.purchaseorder.application.usecase.CreatePurchaseOrderUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderStatus;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.vendor.domain.ProductVendor;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePurchaseOrderUseCaseImpl implements CreatePurchaseOrderUseCase {

  private final PurchaseOrderRepository purchaseOrderRepository;

  private final PurchaseOrderLineRepository purchaseOrderLineRepository;

  private final VendorRepository vendorRepository;

  private final ProductVendorRepository productVendorRepository;

  @Override
  public PurchaseOrder apply(CreatePurchaseOrderCommand command) {
    Vendor vendor =
        vendorRepository
            .findById(command.getSupplierId())
            .orElseThrow(
                () ->
                    new VendorNotFoundException(
                        "Vendor not found with id " + command.getSupplierId()));

    var productsIdToOrder =
        command.getPurchaseOrderLines().stream()
            .map(PurchaseOrderLineRequest::getProductId)
            .toList();
    var productsVendorToOrder =
        productVendorRepository.findBySupplierIdAndProductIdIn(vendor.getId(), productsIdToOrder);

    PurchaseOrder savedPurchaseOrder =
        purchaseOrderRepository.save(buildPurchaseOrder(command, vendor));
    var savedLines =
        purchaseOrderLineRepository.saveAll(
            buildPurchaseOrderLines(command, productsVendorToOrder, savedPurchaseOrder));
    savedPurchaseOrder.setPurchaseOrderLines(savedLines);
    return savedPurchaseOrder;
  }

  private PurchaseOrder buildPurchaseOrder(CreatePurchaseOrderCommand command, Vendor vendor) {
    return PurchaseOrder.builder()
        .createdAt(Instant.now())
        .vendor(vendor)
        .status(PurchaseOrderStatus.PENDING)
        .estimatedDeliveryDate(command.getEstimatedDeliveryDate())
        .build();
  }

  private List<PurchaseOrderLine> buildPurchaseOrderLines(
      CreatePurchaseOrderCommand command,
      List<ProductVendor> productsVendorToOrder,
      PurchaseOrder purchaseOrder) {
    return command.getPurchaseOrderLines().stream()
        .map(
            lineRequest ->
                buildPurchaseOrderLine(lineRequest, productsVendorToOrder, purchaseOrder))
        .toList();
  }

  private PurchaseOrderLine buildPurchaseOrderLine(
      PurchaseOrderLineRequest lineRequest,
      List<ProductVendor> productsVendorToOrder,
      PurchaseOrder purchaseOrder) {
    return PurchaseOrderLine.builder()
        .product(
            productsVendorToOrder.stream()
                .map(ProductVendor::getProduct)
                .filter(product -> product.getId().equals(lineRequest.getProductId()))
                .findFirst()
                .orElseThrow())
        .unitPrice(lineRequest.getUnitPrice())
        .quantity(lineRequest.getQuantity())
        .createdAt(Instant.now())
        .purchaseOrder(purchaseOrder)
        .build();
  }
}
