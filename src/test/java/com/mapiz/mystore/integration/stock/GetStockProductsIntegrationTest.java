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

  private static final String FULL_DESCRIPTION_FORMAT = "%s %s - %s";
  @SpyBean private JpaStockItemRepository stockItemRepository;

  @Test
  void testGetStockProducts() throws Exception {
    // Arrange
    var expectedResponse =
        List.of(
            StockItemSummaryResponse.builder()
                .vendorProductId(KIKES_EGGS)
                .productId(EGG_ID)
                .fullDescription(
                    FULL_DESCRIPTION_FORMAT.formatted(EGG_NAME, EGG_DESCRIPTION, KIKES_NAME))
                .vendorId(KIKES_ID)
                .quantity(UNITS_OF_EGGS_IN_STOCK)
                .weightedCost(BigDecimalUtils.valueOf("354.2"))
                .baseUnitSymbol(UNIT_SYMBOL)
                .build(),
            StockItemSummaryResponse.builder()
                .vendorProductId(ZENU_SAUSAGES)
                .productId(SAUSAGE_ID)
                .fullDescription(
                    FULL_DESCRIPTION_FORMAT.formatted(SAUSAGE_NAME, SAUSAGE_DESCRIPTION, ZENU_NAME))
                .vendorId(ZENU_ID)
                .quantity(UNITS_OF_SAUSAGES_IN_STOCK)
                .weightedCost(BigDecimal.valueOf(2500.0))
                .baseUnitSymbol(UNIT_SYMBOL)
                .build(),
            StockItemSummaryResponse.builder()
                .vendorProductId(ALQUERIA_MILKS)
                .productId(MILK_ID)
                .vendorId(ALQUERIA_ID)
                .fullDescription(
                    FULL_DESCRIPTION_FORMAT.formatted(MILK_NAME, MILK_DESCRIPTION, ALQUERIA_NAME))
                .quantity(UNITS_OF_MILK_IN_STOCK)
                .weightedCost(BigDecimalUtils.valueOf(5000.0))
                .baseUnitSymbol(UNIT_SYMBOL)
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
