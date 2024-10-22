package com.mapiz.mystore.purchaseorder.application.usecase.impl;

import com.mapiz.mystore.purchaseorder.application.command.CreatePurchaseOrderCommand;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineRequest;
import com.mapiz.mystore.purchaseorder.application.usecase.CreatePurchaseOrderUseCase;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrder;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderStatus;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.domain.repository.PurchaseOrderRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.VendorProduct;
import com.mapiz.mystore.vendor.domain.repository.ProductVendorRepository;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import java.math.BigDecimal;
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
    var total =
        command.getPurchaseOrderLines().stream()
            .map(line -> BigDecimalUtils.multiply(line.getUnitPrice(), line.getQuantity()))
            .reduce(BigDecimal.ZERO, BigDecimalUtils::add);

    return PurchaseOrder.builder()
        .createdAt(Instant.now())
        .vendor(vendor)
        .status(PurchaseOrderStatus.PENDING)
        .total(total)
        .estimatedDeliveryDate(command.getEstimatedDeliveryDate())
        .build();
  }

  private List<PurchaseOrderLine> buildPurchaseOrderLines(
      CreatePurchaseOrderCommand command,
      List<VendorProduct> productsVendorToOrder,
      PurchaseOrder purchaseOrder) {
    return command.getPurchaseOrderLines().stream()
        .map(
            lineRequest ->
                buildPurchaseOrderLine(lineRequest, productsVendorToOrder, purchaseOrder))
        .toList();
  }

  private PurchaseOrderLine buildPurchaseOrderLine(
      PurchaseOrderLineRequest lineRequest,
      List<VendorProduct> vendorProductsToOrder,
      PurchaseOrder purchaseOrder) {
    return PurchaseOrderLine.builder()
        .vendorProduct(
            vendorProductsToOrder.stream()
                .filter(
                    vendorProduct ->
                        vendorProduct.getProduct().getId().equals(lineRequest.getProductId()))
                .findFirst()
                .orElseThrow())
        .unitPrice(lineRequest.getUnitPrice())
        .quantity(lineRequest.getQuantity())
        .createdAt(Instant.now())
        .purchaseOrder(purchaseOrder)
        .build();
  }
}
