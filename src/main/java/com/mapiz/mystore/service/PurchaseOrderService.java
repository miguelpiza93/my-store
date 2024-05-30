package com.mapiz.mystore.service;

import com.mapiz.mystore.dto.PurchaseOrderDTO;
import com.mapiz.mystore.entity.Product;
import com.mapiz.mystore.entity.PurchaseOrder;
import com.mapiz.mystore.entity.Supplier;
import com.mapiz.mystore.entity.SupplierProduct;
import com.mapiz.mystore.repository.IPurchaseOrderRepository;
import com.mapiz.mystore.repository.ISupplierProductRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PurchaseOrderService {

    @Autowired
    private ISupplierProductRepository supplierProductRepository;

    @Autowired
    private IPurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder create(PurchaseOrderDTO purchaseOrderDTO) {
        SupplierProduct  supplierProduct = supplierProductRepository.findBySupplierIdAndProductId(
                purchaseOrderDTO.getSupplierId(), purchaseOrderDTO.getProductId()
        ).orElseThrow();

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .createdAt(Instant.now())
                .product(Hibernate.unproxy(supplierProduct.getProduct(), Product.class))
                .supplier(Hibernate.unproxy(supplierProduct.getSupplier(), Supplier.class))
                .unitPrice(supplierProduct.getPrice())
                .quantity(purchaseOrderDTO.getQuantity())
                .build();
        return purchaseOrderRepository.save(purchaseOrder);
    }
}
