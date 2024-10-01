package com.mapiz.mystore.integration.purchaseorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderStatus;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ReceivePurchaseOrderIntegrationTest extends BaseIntegrationTest {

  private static final int PRODUCT_ID = 1;
  private static final String PRODUCT_NAME = "Product 1";
  private static final int VENDOR_ID = 1;
  private static final String VENDOR_NAME = "Vendor 1";
  private static final int PURCHASE_ORDER_LINE_ID = 1;
  private static final double PURCHASE_ORDER_LINE_UNIT_PRICE = 10.0;
  private static final int PRODUCT_QUANTITY = 1;
  private static final int PURCHASE_ORDER_ID = 1;
  private static final int EXISTING_STOCK_ID = 65;
  private static final int EXISTING_STOCK_QUANTITY = 3;

  @MockBean private JpaPurchaseOrderRepository purchaseOrderRepository;

  @MockBean private JpaStockItemRepository stockItemRepository;

  private ProductEntity savedProduct;
  private VendorEntity savedVendor;
  private PurchaseOrderEntity savedPurchaseOrder;

  @BeforeEach
  void setUp() {
    savedProduct = prepareProduct();
    savedVendor = prepareVendor();
    savedPurchaseOrder = preparePurchaseOrder();
  }

  @Test
  void testReceivePurchaseOrderWhenStockIsEmpty() throws Exception {
    // Arrange
    when(purchaseOrderRepository.findById(PURCHASE_ORDER_ID))
        .thenReturn(Optional.of(savedPurchaseOrder));
    when(stockItemRepository.findByProductIdIn(anyCollection())).thenReturn(List.of());

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(getReceiveUrl()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    var expectedSavedStockItems = List.of(createStockItem(PRODUCT_QUANTITY));
    verifyRepositoriesInteractions(expectedSavedStockItems);
  }

  @Test
  void testReceivePurchaseOrderWhenStockIsNotEmpty() throws Exception {
    // Arrange
    when(purchaseOrderRepository.findById(PURCHASE_ORDER_ID))
        .thenReturn(Optional.of(savedPurchaseOrder));
    when(stockItemRepository.findByProductIdIn(anyCollection()))
        .thenReturn(List.of(createStockItemWithId(EXISTING_STOCK_ID, EXISTING_STOCK_QUANTITY)));

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(getReceiveUrl()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    var expectedQuantity = PRODUCT_QUANTITY + EXISTING_STOCK_QUANTITY;
    var expectedSavedStockItems =
        List.of(createStockItemWithId(EXISTING_STOCK_ID, expectedQuantity));
    verifyRepositoriesInteractions(expectedSavedStockItems);
  }

  @Test
  void testReceivePurchaseOrderWhenIsAlreadyReceived() throws Exception {
    // Arrange
    savedPurchaseOrder.setStatus(PurchaseOrderStatus.RECEIVED.name());
    when(purchaseOrderRepository.findById(PURCHASE_ORDER_ID))
        .thenReturn(Optional.of(savedPurchaseOrder));

    // Act & Assert
    var expectedApiError =
        new ApiError(
            "bad_request",
            "Purchase order with id 1 was already received",
            HttpStatus.BAD_REQUEST.value());

    mockMvc
        .perform(MockMvcRequestBuilders.post(getReceiveUrl()))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));
  }

  private ProductEntity prepareProduct() {
    return ProductEntity.builder().id(PRODUCT_ID).name(PRODUCT_NAME).build();
  }

  private VendorEntity prepareVendor() {
    return VendorEntity.builder().id(VENDOR_ID).name(VENDOR_NAME).build();
  }

  private PurchaseOrderEntity preparePurchaseOrder() {
    return PurchaseOrderEntity.builder()
        .id(PURCHASE_ORDER_ID)
        .status(PurchaseOrderStatus.PENDING.name())
        .purchaseOrderLines(List.of(preparePurchaseOrderLine()))
        .supplier(savedVendor)
        .build();
  }

  private PurchaseOrderLineEntity preparePurchaseOrderLine() {
    return PurchaseOrderLineEntity.builder()
        .id(PURCHASE_ORDER_LINE_ID)
        .product(savedProduct)
        .quantity(PRODUCT_QUANTITY)
        .unitPrice(PURCHASE_ORDER_LINE_UNIT_PRICE)
        .build();
  }

  private StockItemEntity createStockItem(int quantity) {
    return StockItemEntity.builder().product(savedProduct).quantity(quantity).build();
  }

  private StockItemEntity createStockItemWithId(int id, int quantity) {
    return StockItemEntity.builder().id(id).product(savedProduct).quantity(quantity).build();
  }

  private String getReceiveUrl() {
    return EndpointConstant.PURCHASE_ORDER_BASE_PATH + "/" + PURCHASE_ORDER_ID + "/receive";
  }

  private void verifyRepositoriesInteractions(List<StockItemEntity> expectedStockItems) {
    ArgumentCaptor<PurchaseOrderEntity> purchaseOrderCaptor =
        ArgumentCaptor.forClass(PurchaseOrderEntity.class);
    verify(purchaseOrderRepository, times(1)).save(purchaseOrderCaptor.capture());
    verify(stockItemRepository, times(1)).saveAll(expectedStockItems);
    var capturedPurchaseOrder = purchaseOrderCaptor.getValue();
    assertEquals(PurchaseOrderStatus.RECEIVED.name(), capturedPurchaseOrder.getStatus());
  }
}
