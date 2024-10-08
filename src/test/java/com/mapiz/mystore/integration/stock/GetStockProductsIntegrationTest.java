package com.mapiz.mystore.integration.stock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.stock.application.dto.StockItemSummary;
import com.mapiz.mystore.stock.infrastructure.EndpointConstant;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import java.math.BigDecimal;
import java.util.Collections;
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
        Collections.singletonList(
            StockItemSummary.builder()
                .productId(1)
                .productName("Huevos")
                .quantity(BigDecimal.valueOf(4))
                .salePrice(0.0)
                .weightedCost(BigDecimal.valueOf(425))
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
