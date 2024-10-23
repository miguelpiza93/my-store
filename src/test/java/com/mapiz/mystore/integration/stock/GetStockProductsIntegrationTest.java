package com.mapiz.mystore.integration.stock;

import static com.mapiz.mystore.integration.Constants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.stock.application.dto.StockItemSummaryResponse;
import com.mapiz.mystore.stock.infrastructure.EndpointConstant;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetStockProductsIntegrationTest extends BaseIntegrationTest {

  @SpyBean private JpaStockItemRepository stockItemRepository;

  @Test
  void testGetStockProducts() throws Exception {
    // Arrange
    var expectedResponse =
        List.of(
            StockItemSummaryResponse.builder()
                .vendorProductId(KIKES_EGGS)
                .productId(EGG_ID)
                .productName(EGG_NAME)
                .vendorName(KIKES_NAME)
                .vendorId(KIKES_ID)
                .quantity(UNITS_OF_EGGS_IN_STOCK)
                .weightedCost(BigDecimalUtils.valueOf("354.2"))
                .build(),
            StockItemSummaryResponse.builder()
                .vendorProductId(ZENU_SAUSAGES)
                .productName(SAUSAGE_NAME)
                .productId(SAUSAGE_ID)
                .vendorName(ZENU_NAME)
                .vendorId(ZENU_ID)
                .quantity(UNITS_OF_SAUSAGES_IN_STOCK)
                .weightedCost(BigDecimal.valueOf(2500.0))
                .build(),
            StockItemSummaryResponse.builder()
                .vendorProductId(ALQUERIA_MILKS)
                .productName(MILK_NAME)
                .productId(MILK_ID)
                .vendorName(ALQUERIA_NAME)
                .vendorId(ALQUERIA_ID)
                .quantity(UNITS_OF_MILK_IN_STOCK)
                .weightedCost(BigDecimalUtils.valueOf(5000.0))
                .build());

    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.STOCK_BASE_PATH + "/summary"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }

  @Test
  void testGetProductsReturnsEmptyListWhenNoProducts() throws Exception {
    // Arrange
    when(stockItemRepository.findAllAvailable()).thenReturn(List.of());

    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.STOCK_BASE_PATH + "/summary"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[]"));
  }
}
