package com.mapiz.mystore.integration.vendor;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class DeleteVendorIntegrationTest extends BaseIntegrationTest {

  @MockBean private JpaVendorRepository vendorRepository;

  @Test
  void testDeleteExistingVendor() throws Exception {
    // Arrange
    var idToDelete = 1;
    var vendorToDelete = VendorEntity.builder().id(1).name("Product 1").build();
    when(vendorRepository.findById(idToDelete)).thenReturn(Optional.ofNullable(vendorToDelete));

    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.delete(EndpointConstant.BASE_PATH + "/" + idToDelete))
        .andExpect(status().isNoContent());

    // verify repository deleteById call
    verify(vendorRepository, times(1)).deleteById(idToDelete);
  }

  @Test
  void testDeleteNonExistentVendor() throws Exception {
    // Arrange
    var idToDelete = 1;
    when(vendorRepository.findById(idToDelete)).thenReturn(Optional.empty());
    var expectedApiError =
        new ApiError("resource_not_found", "Vendor not found", HttpStatus.NOT_FOUND.value());

    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.delete(EndpointConstant.BASE_PATH + "/" + idToDelete))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    // verify repository deleteById call
    verify(vendorRepository, never()).deleteById(idToDelete);
  }
}
