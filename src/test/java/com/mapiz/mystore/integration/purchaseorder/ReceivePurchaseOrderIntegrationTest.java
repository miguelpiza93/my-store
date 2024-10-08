package com.mapiz.mystore.integration.purchaseorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.purchaseorder.domain.PurchaseOrderStatus;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ReceivePurchaseOrderIntegrationTest extends BaseIntegrationTest {

  private static final int EGG_ID = 1;
  private static final int UNITS_OF_EGGS_IN_STOCK = 4;
  private static final int EXPECTED_UNITS_OF_EGGS_ADDED = 120;

  private static final int MILK_ID = 2;
  private static final int UNITS_OF_MILK_IN_STOCK = 0;
  private static final int EXPECTED_UNITS_OF_MILK_ADDED = 12;

  private static final int PURCHASE_ORDER_ID_RECEIVED = 1;
  private static final int PURCHASE_ORDER_OF_EGGS_ID = 2;
  private static final int PURCHASE_ORDER_OF_MILK_ID = 3;

  @SpyBean private JpaPurchaseOrderRepository purchaseOrderRepository;

  @SpyBean private JpaStockItemRepository stockItemRepository;

  @Test
  void testReceivePurchaseOrderWhenStockIsEmpty() throws Exception {
    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(getReceiveUrl(PURCHASE_ORDER_OF_MILK_ID))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());
    Map<Integer, Integer> expectedQuantitiesByProductId =
        Map.of(MILK_ID, UNITS_OF_MILK_IN_STOCK + EXPECTED_UNITS_OF_MILK_ADDED);

    verifyDataSaved(PURCHASE_ORDER_OF_MILK_ID, expectedQuantitiesByProductId);
  }

  @Test
  void testReceivePurchaseOrderWhenStockIsNotEmpty() throws Exception {
    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(getReceiveUrl(PURCHASE_ORDER_OF_EGGS_ID))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    Map<Integer, Integer> expectedQuantitiesByProductId =
        Map.of(EGG_ID, UNITS_OF_EGGS_IN_STOCK + EXPECTED_UNITS_OF_EGGS_ADDED);

    verifyDataSaved(PURCHASE_ORDER_OF_EGGS_ID, expectedQuantitiesByProductId);
  }

  @Test
  void testReceivePurchaseOrderWhenIsAlreadyReceived() throws Exception {
    // Act & Assert
    var expectedApiError =
        new ApiError(
            "bad_request",
            "Purchase order with id 1 was already received",
            HttpStatus.BAD_REQUEST.value());

    mockMvc
        .perform(MockMvcRequestBuilders.post(getReceiveUrl(PURCHASE_ORDER_ID_RECEIVED)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));
  }

  private String getReceiveUrl(int purchaseOrderId) {
    return EndpointConstant.PURCHASE_ORDER_BASE_PATH + "/" + purchaseOrderId + "/receive";
  }

  private void verifyDataSaved(
      int purchaseOrderId, Map<Integer, Integer> expectedQuantitiesByProductId) {
    // 1. Verificar que la orden de compra fue actualizada a estado "RECEIVED"
    PurchaseOrderEntity savedOrder =
        purchaseOrderRepository.findById(purchaseOrderId).orElseThrow();
    assertEquals(PurchaseOrderStatus.RECEIVED.name(), savedOrder.getStatus());

    var productsId =
        savedOrder.getPurchaseOrderLines().stream().map(line -> line.getProduct().getId()).toList();

    Map<Integer, Integer> quantitiesByProductId =
        stockItemRepository.findAll().stream()
            .filter(item -> productsId.contains(item.getPurchaseOrderLine().getProduct().getId()))
            .collect(
                Collectors.toMap(
                    item -> item.getPurchaseOrderLine().getProduct().getId(),
                    StockItemEntity::getQuantity,
                    Integer::sum));

    // 2. Verificar que los items de stock se han guardado correctamente
    for (PurchaseOrderLineEntity line : savedOrder.getPurchaseOrderLines()) {
      var expectedQuantity =
          expectedQuantitiesByProductId.getOrDefault(line.getProduct().getId(), 0);
      assertEquals(
          expectedQuantity, quantitiesByProductId.getOrDefault(line.getProduct().getId(), 0));
    }

    verify(purchaseOrderRepository, times(1)).save(any(PurchaseOrderEntity.class));
    verify(stockItemRepository, times(1)).saveAll(anyList());
  }
}
