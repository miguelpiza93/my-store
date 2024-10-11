package com.mapiz.mystore.integration.purchaseorder;

import static com.mapiz.mystore.integration.Constants.*;
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
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ReceivePurchaseOrderIntegrationTest extends BaseIntegrationTest {

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
    Map<Integer, BigDecimal> expectedQuantitiesByProductId =
        Map.of(MILK_ID, BigDecimalUtils.add(UNITS_OF_MILK_IN_STOCK, EXPECTED_UNITS_OF_MILK_ADDED));

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

    Map<Integer, BigDecimal> expectedQuantitiesByProductId =
        Map.of(EGG_ID, BigDecimalUtils.add(UNITS_OF_EGGS_IN_STOCK, EXPECTED_UNITS_OF_EGGS_ADDED));

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
      int purchaseOrderId, Map<Integer, BigDecimal> expectedQuantitiesByProductId) {
    // 1. Verificar que la orden de compra fue actualizada a estado "RECEIVED"
    PurchaseOrderEntity savedOrder =
        purchaseOrderRepository.findById(purchaseOrderId).orElseThrow();
    assertEquals(PurchaseOrderStatus.RECEIVED.name(), savedOrder.getStatus());

    var productsId =
        savedOrder.getPurchaseOrderLines().stream().map(line -> line.getProduct().getId()).toList();

    Map<Integer, BigDecimal> quantitiesByProductId =
        stockItemRepository.findAll().stream()
            .filter(item -> productsId.contains(item.getPurchaseOrderLine().getProduct().getId()))
            .collect(
                Collectors.toMap(
                    item -> item.getPurchaseOrderLine().getProduct().getId(),
                    StockItemEntity::getQuantity,
                    BigDecimalUtils::add));

    // 2. Verificar que los items de stock se han guardado correctamente
    for (PurchaseOrderLineEntity line : savedOrder.getPurchaseOrderLines()) {
      var expectedQuantity =
          expectedQuantitiesByProductId.getOrDefault(line.getProduct().getId(), BigDecimal.ZERO);
      assertEquals(
          expectedQuantity,
          quantitiesByProductId.getOrDefault(line.getProduct().getId(), BigDecimal.ZERO));
    }

    verify(purchaseOrderRepository, times(1)).save(any(PurchaseOrderEntity.class));
    verify(stockItemRepository, times(1)).saveAll(anyList());
  }
}
