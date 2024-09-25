package com.mapiz.mystore.integration.purchaseorder;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineResponse;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderLine;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.vendor.application.dto.CreateVendorRequest;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.ProductVendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaProductVendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreatePurchaseOrderIntegrationTest extends BaseIntegrationTest {

    private static final String VENDOR_NAME = "Vendor 1";
    private static final int VENDOR_ID = 1;
    @MockBean
    private JpaVendorRepository vendorRepository;
    @MockBean
    private JpaPurchaseOrderRepository purchaseOrderRepository;

    @MockBean
    private JpaPurchaseOrderLineRepository purchaseOrderLineRepository;

    @MockBean
    private JpaProductVendorRepository productVendorRepository;

    @Test
    void testCreatePurchaseOrder() throws Exception {
        // Arrange
        var savedProduct = ProductEntity.builder().id(1).name("Product 1").build();

        var savedVendor = VendorEntity.builder().id(VENDOR_ID).name(VENDOR_NAME).build();
        when(vendorRepository.findById(VENDOR_ID)).thenReturn(Optional.of(savedVendor));
        when(productVendorRepository.findByVendorIdAndProductIdIn(VENDOR_ID, List.of(1)))
                .thenReturn(List.of(
                        ProductVendorEntity.builder()
                                .id(1)
                                .product(savedProduct)
                                .vendor(savedVendor)
                                .price(10.0).build()
                ));

        var now = Instant.now();
        var estimatedDeliveryDate = LocalDate.now().plusDays(5);

        var savedPurchaseOrder = PurchaseOrderEntity.builder()
                .id(1)
                .createdAt(now)
                .supplier(savedVendor)
                .estimatedDeliveryDate(estimatedDeliveryDate)
                .build();
        when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(savedPurchaseOrder);
        when(purchaseOrderLineRepository.saveAll(anyList())).thenReturn(
                List.of(
                        PurchaseOrderLineEntity.builder()
                                .id(1)
                                .quantity(20)
                                .unitPrice(10.0)
                                .product(savedProduct)
                                .build()
                )
        );

        var request = CreatePurchaseOrderRequest.builder()
                .supplierId(VENDOR_ID)
                .estimatedDeliveryDate(estimatedDeliveryDate)
                .purchaseOrderLines(
                        List.of(
                                PurchaseOrderLineRequest.builder()
                                        .productId(1)
                                        .quantity(20)
                                        .build()
                        )
                )
                .build();

        // Act & Assert
        var expectedPurchaseOrder = PurchaseOrderResponse.builder()
                .id(1)
                .supplierName(VENDOR_NAME)
                .createdAt(now)
                .estimatedDeliveryDate(estimatedDeliveryDate)
                .purchaseOrderLines(
                        List.of(
                                PurchaseOrderLineResponse.builder()
                                        .id(1)
                                        .unitPrice(10.0)
                                        .quantity(20)
                                        .product(
                                                Product.builder()
                                                        .id(1)
                                                        .name("Product 1")
                                                        .build()
                                        )
                                        .build()
                        )
                )
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(EndpointConstant.PURCHASE_ORDER_BASE_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPurchaseOrder)));
    }
}
