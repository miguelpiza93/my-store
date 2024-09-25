package com.mapiz.mystore.integration.product;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.infrastructure.EndpointConstant;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetProductsIntegrationTest extends BaseIntegrationTest {

    @MockBean
    private JpaProductRepository productRepository;

    @Test
    void testGetProductsReturnsListOfProducts() throws Exception {
        // Arrange
        List<ProductEntity> existingProducts = Arrays.asList(
                ProductEntity.builder().id(1).name("Product 1").description("product one").build(),
                ProductEntity.builder().id(2).name("Product 2").description("product two").build()
        );
        when(productRepository.findAll()).thenReturn(existingProducts);

        // Act & Assert
        var expectedProducts = Arrays.asList(
                ProductResponse.builder().id(1).name("Product 1").description("product one").build(),
                ProductResponse.builder().id(2).name("Product 2").description("product two").build()
        );
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointConstant.PRODUCTS_BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedProducts)));
    }

    @Test
    void testGetProductsReturnsEmptyListWhenNoProducts() throws Exception {
        // Arrange
        when(productRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get(EndpointConstant.PRODUCTS_BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }
}
