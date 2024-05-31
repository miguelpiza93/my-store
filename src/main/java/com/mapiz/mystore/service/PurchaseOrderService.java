package com.mapiz.mystore.service;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.dto.PurchaseOrderLineDTO;
import com.mapiz.mystore.entity.*;
import com.mapiz.mystore.exception.*;
import com.mapiz.mystore.repository.IPurchaseOrderRepository;
import com.mapiz.mystore.repository.ISupplierProductRepository;
import com.mapiz.mystore.repository.ISupplierRepository;
import com.mapiz.mystore.mapper.PurchaseOrderMapper;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PurchaseOrderService {

    private final ISupplierProductRepository supplierProductRepository;
    private final ISupplierRepository supplierRepository;
    private final IPurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Transactional
    public PurchaseOrder create(PurchaseOrderDTO purchaseOrderDTO) throws BusinessException {
        validatePurchaseOrderDTO(purchaseOrderDTO);

        Supplier supplier = supplierRepository.findById(purchaseOrderDTO.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id " + purchaseOrderDTO.getSupplierId()));

        Map<Integer, SupplierProduct> supplierProducts = getSupplierProducts(purchaseOrderDTO, supplier);

        PurchaseOrder purchaseOrder = buildPurchaseOrder(purchaseOrderDTO, supplier);
        List<PurchaseOrderLine> purchaseOrderLines = buildPurchaseOrderLines(purchaseOrderDTO, purchaseOrder, supplierProducts);

        purchaseOrder.setPurchaseOrderLines(purchaseOrderLines);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    private void validatePurchaseOrderDTO(PurchaseOrderDTO purchaseOrderDTO) throws BusinessException {
        if (purchaseOrderDTO.getPurchaseOrderLines().isEmpty()) {
            throw new BusinessException("Products are required");
        }
    }

    private Map<Integer, SupplierProduct> getSupplierProducts(PurchaseOrderDTO purchaseOrderDTO, Supplier supplier) throws ProductNotFoundException {
        List<Integer> productIds = purchaseOrderDTO.getPurchaseOrderLines().stream()
                .map(PurchaseOrderLineDTO::getProductId)
                .collect(Collectors.toList());

        Map<Integer, SupplierProduct> supplierProducts = supplierProductRepository.findBySupplierIdAndProductIdIn(
                purchaseOrderDTO.getSupplierId(), productIds
        ).stream().collect(Collectors.toMap(
                supplierProduct -> supplierProduct.getProduct().getId(),
                supplierProduct -> supplierProduct
        ));

        if (supplierProducts.size() != productIds.size()) {
            throw new ProductNotFoundException("One or more products not found");
        }

        return supplierProducts;
    }

    private PurchaseOrder buildPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, Supplier supplier) {
        return PurchaseOrder.builder()
                .createdAt(Instant.now())
                .supplier(supplier)
                .estimatedDeliveryDate(purchaseOrderDTO.getEstimatedDeliveryDate())
                .build();
    }

    private List<PurchaseOrderLine> buildPurchaseOrderLines(PurchaseOrderDTO purchaseOrderDTO, PurchaseOrder purchaseOrder, Map<Integer, SupplierProduct> supplierProducts) {
        return purchaseOrderDTO.getPurchaseOrderLines().stream().map(
                orderLineDTO -> PurchaseOrderLine.builder()
                        .purchaseOrder(purchaseOrder)
                        .unitPrice(supplierProducts.get(orderLineDTO.getProductId()).getPrice())
                        .product(Hibernate.unproxy(supplierProducts.get(orderLineDTO.getProductId()).getProduct(), Product.class))
                        .quantity(orderLineDTO.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findById(Integer id) throws BusinessException {
        return purchaseOrderRepository.findById(id).orElseThrow(() -> new BusinessException("Purchase order not found with id " + id));
    }
}
