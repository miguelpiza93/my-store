package com.mapiz.mystore.integration.product;

import static com.mapiz.mystore.integration.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.application.dto.ProductDetailResponse;
import com.mapiz.mystore.product.infrastructure.EndpointConstant;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetProductIntegrationTest extends BaseIntegrationTest {

  @Test
  void testGetProduct() throws Exception {
    // Arrange
    var expectedResponse =
        ProductDetailResponse.builder()
            .id(EGG_ID)
            .name(EGG_NAME)
            .description(EGG_DESCRIPTION)
            .referenceUnit(CARTON_NAME)
            .build();
    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.BASE_PATH + "/" + EGG_ID))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }
}
