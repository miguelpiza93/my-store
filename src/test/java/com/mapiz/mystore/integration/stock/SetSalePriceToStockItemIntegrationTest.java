package com.mapiz.mystore.integration.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.stock.application.dto.SetSalePriceToStockProductRequest;
import com.mapiz.mystore.stock.infrastructure.EndpointConstant;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SetSalePriceToStockItemIntegrationTest extends BaseIntegrationTest {

  @SpyBean private JpaStockItemRepository jpaStockItemRepository;

  @Test
  void testSetSalePriceToStockItem() throws Exception {
    // Arrange
    var productIdToSetSalePrice = 1;
    int newSalePrice = 1000;
    var request = SetSalePriceToStockProductRequest.builder().salePrice(newSalePrice).build();
    // Act
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    EndpointConstant.STOCK_BASE_PATH + "/products/" + productIdToSetSalePrice)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    // Assert
    verify(jpaStockItemRepository, times(1)).saveAll(any());
    var stockItems = jpaStockItemRepository.findByProductId(productIdToSetSalePrice);
    for (var stockItem : stockItems) {
      assertEquals(newSalePrice, stockItem.getSalePrice());
    }
  }

  @Test
  void testSetSaleToUnExistingPriceToStockItem() throws Exception {
    // Arrange
    var stockItemIdToUpdate = 0;
    int newSalePrice = 1000;
    var request = SetSalePriceToStockProductRequest.builder().salePrice(newSalePrice).build();

    // Act
    var expectedApiError =
        new ApiError(
            "resource_not_found",
            "Stock item for product id 0 not found",
            HttpStatus.NOT_FOUND.value());
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    EndpointConstant.STOCK_BASE_PATH + "/products/" + stockItemIdToUpdate)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    // Assert
    verify(jpaStockItemRepository, never()).save(any());
  }
}
