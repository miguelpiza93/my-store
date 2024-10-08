package com.mapiz.mystore.integration.product;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.infrastructure.EndpointConstant;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductRepository;
import com.mapiz.mystore.shared.ApiError;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class DeleteProductIntegrationTest extends BaseIntegrationTest {

  @MockBean private JpaProductRepository productRepository;

  @Test
  void testDeleteExistingProduct() throws Exception {
    // Arrange
    var idToDelete = 1;
    var productToDelete =
        ProductEntity.builder().id(1).name("Product 1").description("product one").build();
    when(productRepository.findById(idToDelete)).thenReturn(Optional.ofNullable(productToDelete));

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete(EndpointConstant.PRODUCTS_BASE_PATH + "/" + idToDelete))
        .andExpect(status().isNoContent());

    // verify repository deleteById call
    verify(productRepository, times(1)).deleteById(idToDelete);
  }

  @Test
  void testDeleteNonExistentProduct() throws Exception {
    // Arrange
    var idToDelete = 1;
    when(productRepository.findById(idToDelete)).thenReturn(Optional.empty());
    var expectedApiError =
        new ApiError(
            "resource_not_found", "Product with id 1 not found", HttpStatus.NOT_FOUND.value());

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete(EndpointConstant.PRODUCTS_BASE_PATH + "/" + idToDelete))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    // verify repository deleteById call
    verify(productRepository, never()).deleteById(idToDelete);
  }
}
