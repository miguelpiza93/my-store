package com.mapiz.mystore.integration.product;

import static com.mapiz.mystore.integration.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.application.dto.ProductDetailResponse;
import com.mapiz.mystore.product.application.dto.ProductPriceDetail;
import com.mapiz.mystore.product.infrastructure.EndpointConstant;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetProductIntegrationTest extends BaseIntegrationTest {

  @Test
  void testGetProductWithPrices() throws Exception {
    // Arrange
    var expectedResponse =
        ProductDetailResponse.builder()
            .id(EGG_ID)
            .name(EGG_NAME)
            .description(EGG_DESCRIPTION)
            .referenceUnit(CARTON_NAME)
            .prices(
                List.of(
                    new ProductPriceDetail(UNIT_ID, UNIT_NAME, BigDecimalUtils.valueOf("500.0")),
                    new ProductPriceDetail(
                        CARTON_ID, CARTON_NAME, BigDecimalUtils.valueOf("12000.0")),
                    new ProductPriceDetail(
                        HALF_CARTON_ID, HALF_CARTON_NAME, BigDecimalUtils.valueOf("0.0"))))
            .build();
    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.BASE_PATH + "/" + EGG_ID))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }

  @Test
  void testGetProductWithoutPrices() throws Exception {
    // Arrange
    var expectedResponse =
        ProductDetailResponse.builder()
            .id(MILK_ID)
            .name(MILK_NAME)
            .description(MILK_DESCRIPTION)
            .referenceUnit(MILK_PACK_NAME)
            .prices(
                List.of(
                    new ProductPriceDetail(
                        MILK_PACK_ID, MILK_PACK_NAME, BigDecimalUtils.valueOf("0.0")),
                    new ProductPriceDetail(UNIT_ID, UNIT_NAME, BigDecimalUtils.valueOf("0.0"))))
            .build();
    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.BASE_PATH + "/" + MILK_ID))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }
}
