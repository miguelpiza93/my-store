package com.mapiz.mystore.integration.product;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.application.dto.CreateProductRequest;
import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.infrastructure.EndpointConstant;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductRepository;
import com.mapiz.mystore.shared.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class CreateProductIntegrationTest extends BaseIntegrationTest {

  private static final String PRODUCT_NAME = "Product 1";
  private static final String PRODUCT_DESC = "test product";
  private static final Long UNIT_ID = 2L;

  @SpyBean private JpaProductRepository productRepository;

  @Test
  void testCreateProduct() throws Exception {
    var request =
        CreateProductRequest.builder()
            .name(PRODUCT_NAME)
            .description(PRODUCT_DESC)
            .referenceUnitId(UNIT_ID)
            .build();

    // Act & Assert
    var expectedProduct =
        ProductResponse.builder().id(3).name(PRODUCT_NAME).description(PRODUCT_DESC).build();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.PRODUCTS_BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedProduct)));

    verify(productRepository, times(1)).save(any());
  }

  @Test
  void testCreateProductWithNullName() throws Exception {
    // Arrange
    var request = CreateProductRequest.builder().name(null).description(PRODUCT_DESC).build();
    var expectedApiError =
        new ApiError("invalid_request", "name: must not be blank", HttpStatus.BAD_REQUEST.value());

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.PRODUCTS_BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    verify(productRepository, never()).save(any());
  }

  @Test
  void testCreateProductWithNullNameAndWithoutDescription() throws Exception {
    // Arrange
    var request = CreateProductRequest.builder().name(null).description(null).build();
    var expectedApiError =
        new ApiError(
            "invalid_request",
            "description: must not be blank, name: must not be blank",
            HttpStatus.BAD_REQUEST.value());

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.PRODUCTS_BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    verify(productRepository, never()).save(any());
  }
}
