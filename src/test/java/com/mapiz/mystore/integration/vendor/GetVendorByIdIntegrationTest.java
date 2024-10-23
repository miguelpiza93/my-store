package com.mapiz.mystore.integration.vendor;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetVendorByIdIntegrationTest extends BaseIntegrationTest {

  @MockBean private JpaVendorRepository vendorRepository;

  @Test
  void testGetExistingVendor() throws Exception {
    // Arrange
    var idToGet = 1;
    var vendorToDelete = VendorEntity.builder().id(1).name("Product 1").build();
    when(vendorRepository.findById(idToGet)).thenReturn(Optional.ofNullable(vendorToDelete));

    // Act & Assert
    var expectedVendor = VendorResponse.builder().id(1).name("Product 1").build();
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.BASE_PATH + "/" + idToGet))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedVendor)));
  }

  @Test
  void testGetNonExistentVendor() throws Exception {
    // Arrange
    var idToGet = 1;
    when(vendorRepository.findById(idToGet)).thenReturn(Optional.empty());
    var expectedApiError =
        new ApiError("resource_not_found", "Vendor not found", HttpStatus.NOT_FOUND.value());

    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.BASE_PATH + "/" + idToGet))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));
  }
}
