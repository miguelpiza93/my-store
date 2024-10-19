package com.mapiz.mystore.integration.purchaseorder;

import static com.mapiz.mystore.integration.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineResponse;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class CreatePurchaseOrderIntegrationTest extends BaseIntegrationTest {

  private static final BigDecimal PRODUCT_PRICE = BigDecimalUtils.valueOf(12000.0);
  private static final BigDecimal QUANTITY = BigDecimalUtils.valueOf(20);
  public static final LocalDate ESTIMATED_DELIVERY_DATE = LocalDate.now().plusDays(5);

  @Test
  void testCreatePurchaseOrder() throws Exception {
    // Arrange
    var request = buildCreatePurchaseOrderRequest();

    // Act & Assert
    var result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(EndpointConstant.PURCHASE_ORDER_BASE_PATH)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    validateResult(request, result);
  }

  private void validateResult(CreatePurchaseOrderRequest request, MvcResult result)
      throws Exception {
    var response =
        objectMapper.readValue(
            result.getResponse().getContentAsString(), PurchaseOrderResponse.class);
    assert response.getSupplierName().equals(KIKES_NAME);
    assert response.getEstimatedDeliveryDate().equals(request.getEstimatedDeliveryDate());
    validatePurchaseOrderLines(response.getPurchaseOrderLines(), request.getPurchaseOrderLines());
  }

  private void validatePurchaseOrderLines(
      List<PurchaseOrderLineResponse> savedPurchaseOrderLines,
      List<PurchaseOrderLineRequest> requestedOrderLines) {
    assert savedPurchaseOrderLines.size() == requestedOrderLines.size();
    for (PurchaseOrderLineResponse savedPurchaseOrderLine : savedPurchaseOrderLines) {
      var requestedOrderLine =
          requestedOrderLines.stream()
              .filter(
                  line ->
                      line.getProductId()
                          .equals(savedPurchaseOrderLine.getVendorProduct().getProduct().getId()))
              .findFirst()
              .orElseThrow();
      Assertions.assertEquals(
          requestedOrderLine.getQuantity(), savedPurchaseOrderLine.getQuantity());
      Assertions.assertEquals(
          requestedOrderLine.getUnitPrice(), savedPurchaseOrderLine.getUnitPrice());
      Assertions.assertNotNull(savedPurchaseOrderLine.getCreatedAt());
    }
  }

  private CreatePurchaseOrderRequest buildCreatePurchaseOrderRequest() {
    return CreatePurchaseOrderRequest.builder()
        .supplierId(KIKES_ID)
        .estimatedDeliveryDate(ESTIMATED_DELIVERY_DATE)
        .purchaseOrderLines(
            List.of(
                PurchaseOrderLineRequest.builder()
                    .productId(EGG_ID)
                    .quantity(QUANTITY)
                    .unitPrice(PRODUCT_PRICE)
                    .build()))
        .build();
  }
}
