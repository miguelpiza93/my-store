package com.mapiz.mystore.service;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.entity.Product;
import com.mapiz.mystore.entity.PurchaseOrder;
import com.mapiz.mystore.entity.Supplier;
import com.mapiz.mystore.repository.IProductRepository;
import com.mapiz.mystore.repository.IPurchaseOrderRepository;
import com.mapiz.mystore.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PurchaseOrderService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ISupplierRepository  supplierRepository;

    @Autowired
    private IPurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder create(PurchaseOrderDTO purchaseOrderDTO) {
        Product product = productRepository.findById(purchaseOrderDTO.getProductId()).orElseThrow();
        Supplier  supplier = supplierRepository.findById(purchaseOrderDTO.getSupplierId()).orElseThrow();

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .product(product)
                .supplier(supplier)
                .unitPrice(purchaseOrderDTO.getUnitPrice())
                .quantity(purchaseOrderDTO.getQuantity())
                .build();
        return purchaseOrderRepository.save(purchaseOrder);
    }
}
