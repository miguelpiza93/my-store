package com.mapiz.mystore.integration.purchaseorder;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderStatus;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ReceivePurchaseOrderIntegrationTest extends BaseIntegrationTest {

  private static final String PRODUCT_NAME = "Product 1";
  private static final String VENDOR_NAME = "Vendor 1";
  private static final int PRODUCT_QUANTITY = 1;
  private static final int PURCHASE_ORDER_ID = 1;

  @MockBean private JpaPurchaseOrderRepository purchaseOrderRepository;

  @MockBean private JpaStockItemRepository stockItemRepository;

  @Test
  void testReceivePurchaseOrder() throws Exception {
    // Arrange
    var savedProduct = ProductEntity.builder().id(1).name(PRODUCT_NAME).build();
    var savedVendor = VendorEntity.builder().id(1).name(VENDOR_NAME).build();
    var savedPurchaseOrder =
        PurchaseOrderEntity.builder()
            .id(PURCHASE_ORDER_ID)
            .status(PurchaseOrderStatus.PENDING.name())
            .purchaseOrderLines(
                List.of(
                    PurchaseOrderLineEntity.builder()
                        .id(1)
                        .product(savedProduct)
                        .quantity(PRODUCT_QUANTITY)
                        .unitPrice(10.0)
                        .build()))
            .supplier(savedVendor)
            .build();

    when(purchaseOrderRepository.findById(PURCHASE_ORDER_ID))
        .thenReturn(Optional.of(savedPurchaseOrder));

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    EndpointConstant.PURCHASE_ORDER_BASE_PATH
                        + "/"
                        + PURCHASE_ORDER_ID
                        + "/receive")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    var expectedSavedStockItems =
        List.of(
            StockItemEntity.builder()
                .product(ProductEntity.builder().id(1).build())
                .quantity(PRODUCT_QUANTITY)
                .build());

    ArgumentCaptor<PurchaseOrderEntity> purchaseOrderCaptor =
        ArgumentCaptor.forClass(PurchaseOrderEntity.class);
    verify(purchaseOrderRepository, times(1)).save(purchaseOrderCaptor.capture());
    verify(stockItemRepository, times(1)).saveAll(expectedSavedStockItems);
    var capturedPurchaseOrder = purchaseOrderCaptor.getValue();
    Assertions.assertEquals(PurchaseOrderStatus.RECEIVED.name(), capturedPurchaseOrder.getStatus());
  }
}
