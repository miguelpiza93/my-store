package com.mapiz.mystore.integration.purchaseorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.purchaseorder.application.dto.*;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderLineRepository;
import com.mapiz.mystore.shared.ApiError;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class UpdateUnitPricePurchaseOrderLineIntegrationTest extends BaseIntegrationTest {

  @SpyBean private JpaPurchaseOrderLineRepository purchaseOrderLineRepository;

  @Test
  void testCreatePurchaseOrder() throws Exception {
    // Arrange
    var lineToUpdateId = 1;
    var request =
        UpdateUnitPricePurchaseOrderLineRequest.builder().unitPrice(BigDecimal.TEN).build();

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                    EndpointConstant.PURCHASE_ORDER_BASE_PATH + "/1/lines/" + lineToUpdateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    verify(purchaseOrderLineRepository, times(1)).save(any());

    var line = purchaseOrderLineRepository.findById(lineToUpdateId).orElseThrow();
    assertEquals(10.0, line.getUnitPrice());
  }

  @Test
  void testUpdateNonLine() throws Exception {
    // Arrange
    var lineToUpdateId = 0;
    var expectedApiError =
        new ApiError(
            "resource_not_found",
            "Purchase order line with id 0 not found",
            HttpStatus.NOT_FOUND.value());

    // Act & Assert
    var request =
        UpdateUnitPricePurchaseOrderLineRequest.builder().unitPrice(BigDecimal.TEN).build();

    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                    EndpointConstant.PURCHASE_ORDER_BASE_PATH + "/1/lines/" + lineToUpdateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    verify(purchaseOrderLineRepository, never()).save(any());
  }
}
