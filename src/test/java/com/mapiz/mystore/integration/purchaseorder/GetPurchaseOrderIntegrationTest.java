package com.mapiz.mystore.integration.purchaseorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineResponse;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetPurchaseOrderIntegrationTest extends BaseIntegrationTest {

  @Test
  void testGetPurchaseOrder() throws Exception {
    // Arrange
    var purchaseOrderId = 1;

    // Act & Assert
    var result =
        mockMvc
            .perform(MockMvcRequestBuilders.get(EndpointConstant.BASE_PATH + "/" + purchaseOrderId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    validateResult(result);
  }

  private void validateResult(MvcResult result) throws Exception {
    var response =
        objectMapper.readValue(
            result.getResponse().getContentAsString(), PurchaseOrderResponse.class);

    validatePurchaseOrderLines(response.getPurchaseOrderLines());
  }

  private void validatePurchaseOrderLines(List<PurchaseOrderLineResponse> savedPurchaseOrderLines) {
    assertEquals(1, savedPurchaseOrderLines.size());
  }
}
