package com.mapiz.mystore.integration.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.application.dto.SetSalePriceToStockProductRequest;
import com.mapiz.mystore.product.infrastructure.EndpointConstant;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductRepository;
import com.mapiz.mystore.shared.ApiError;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SetSalePriceToProductIntegrationTest extends BaseIntegrationTest {

  @SpyBean private JpaProductRepository jpaProductRepository;

  @Test
  void testSetSalePriceToStockItem() throws Exception {
    // Arrange
    var productIdToSetSalePrice = 1;
    BigDecimal newSalePrice = BigDecimal.valueOf(1000.0);
    var request = SetSalePriceToStockProductRequest.builder().salePrice(newSalePrice).build();
    // Act
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                    EndpointConstant.PRODUCTS_BASE_PATH + "/" + productIdToSetSalePrice)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    // Assert
    verify(jpaProductRepository, times(1)).save(any());
    var product = jpaProductRepository.findById(productIdToSetSalePrice).orElseThrow();
    assertEquals(newSalePrice, product.getSalePrice());
  }

  @Test
  void testSetSaleToUnExistingPriceToStockItem() throws Exception {
    // Arrange
    var productIdToSetSalePrice = 0;
    BigDecimal newSalePrice = BigDecimal.valueOf(1000);
    var request = SetSalePriceToStockProductRequest.builder().salePrice(newSalePrice).build();

    // Act
    var expectedApiError =
        new ApiError(
            "resource_not_found", "Product with id 0 not found", HttpStatus.NOT_FOUND.value());
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                    EndpointConstant.PRODUCTS_BASE_PATH + "/" + productIdToSetSalePrice)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    // Assert
    verify(jpaProductRepository, never()).save(any());
  }
}
