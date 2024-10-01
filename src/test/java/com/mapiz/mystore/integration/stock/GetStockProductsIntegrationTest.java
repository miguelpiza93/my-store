package com.mapiz.mystore.integration.stock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.stock.application.dto.StockItemResponse;
import com.mapiz.mystore.stock.infrastructure.EndpointConstant;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetStockProductsIntegrationTest extends BaseIntegrationTest {

  @MockBean private JpaStockItemRepository stockItemRepository;

  @Test
  void testGetStockProducts() throws Exception {
    // Arrange
    List<StockItemEntity> existingProducts =
        Arrays.asList(
            StockItemEntity.builder()
                .id(1)
                .product(
                    ProductEntity.builder()
                        .id(1)
                        .name("Product 1")
                        .description("product one")
                        .build())
                .quantity(3)
                .salePrice(10.0)
                .build(),
            StockItemEntity.builder()
                .id(2)
                .product(
                    ProductEntity.builder()
                        .id(1)
                        .name("Product 2")
                        .description("product one")
                        .build())
                .quantity(5)
                .salePrice(20.0)
                .build());
    when(stockItemRepository.findAll()).thenReturn(existingProducts);

    // Act & Assert
    var expectedResponse =
        Arrays.asList(
            StockItemResponse.builder()
                .id(1)
                .productName("Product 1")
                .quantity(3)
                .salePrice(10.0)
                .build(),
            StockItemResponse.builder()
                .id(2)
                .productName("Product 2")
                .quantity(5)
                .salePrice(20.0)
                .build());
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.STOCK_BASE_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }

  @Test
  void testGetProductsReturnsEmptyListWhenNoProducts() throws Exception {
    // Arrange
    when(stockItemRepository.findAll()).thenReturn(List.of());

    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.STOCK_BASE_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[]"));
  }
}
