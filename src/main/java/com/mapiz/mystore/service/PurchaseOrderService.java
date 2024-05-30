package com.mapiz.mystore.service;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.dto.PurchaseOrderLineDTO;
import com.mapiz.mystore.entity.*;
import com.mapiz.mystore.repository.IPurchaseOrderRepository;
import com.mapiz.mystore.repository.ISupplierProductRepository;
import com.mapiz.mystore.repository.ISupplierRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private ISupplierProductRepository supplierProductRepository;

    @Autowired
    private ISupplierRepository supplierRepository;

    @Autowired
    private IPurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder create(PurchaseOrderDTO purchaseOrderDTO) {

        Supplier supplier = supplierRepository.findById(purchaseOrderDTO.getSupplierId()).orElseThrow();

        List<Integer> productIds = purchaseOrderDTO.getPurchaseOrderLines().stream().map(
                PurchaseOrderLineDTO::getProductId).toList();

        if (productIds.isEmpty()) {
            throw new RuntimeException("Products is required");
        }

        Map<Integer, SupplierProduct> supplierProducts = supplierProductRepository.findBySupplierIdAndProductIdIn(
                purchaseOrderDTO.getSupplierId(), productIds
        ).stream().collect(Collectors.toMap(
                supplierProduct -> supplierProduct.getProduct().getId(),
                supplierProduct -> supplierProduct
        ));

        if (supplierProducts.size() != productIds.size()) {
            throw new RuntimeException("Products not found");
        }

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .createdAt(Instant.now())
                .supplier(supplier)
                .build();

        List<PurchaseOrderLine> purchaseOrderLines = purchaseOrderDTO.getPurchaseOrderLines().stream().map(
                orderLineDTO -> PurchaseOrderLine.builder()
                        .purchaseOrder(purchaseOrder)
                        .unitPrice(supplierProducts.get(orderLineDTO.getProductId()).getPrice())
                        .product(Hibernate.unproxy(supplierProducts.get(orderLineDTO.getProductId()).getProduct(), Product.class))
                        .quantity(orderLineDTO.getQuantity())
                        .build()
        ).toList();
        purchaseOrder.setPurchaseOrderLines(purchaseOrderLines);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findById(Integer id) {
        return purchaseOrderRepository.findById(id).orElseThrow();
    }
}
