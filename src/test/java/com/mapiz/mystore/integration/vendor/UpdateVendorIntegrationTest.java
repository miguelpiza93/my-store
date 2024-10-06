package com.mapiz.mystore.integration.vendor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.vendor.application.dto.UpdateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class UpdateVendorIntegrationTest extends BaseIntegrationTest {

  private static final String NEW_VENDOR_NAME = "Updated Vendor 1";
  private static final String NEW_VENDOR_PHONE = "1234567";

  @SpyBean private JpaVendorRepository vendorRepository;

  @Test
  void testUpdateVendor() throws Exception {
    // Arrange
    var idToUpdate = 1;

    // Act & Assert
    var request =
        UpdateVendorRequest.builder().name(NEW_VENDOR_NAME).phone(NEW_VENDOR_PHONE).build();
    var expectedVendor =
        VendorResponse.builder()
            .id(idToUpdate)
            .name(NEW_VENDOR_NAME)
            .phone(NEW_VENDOR_PHONE)
            .build();
    mockMvc
        .perform(
            MockMvcRequestBuilders.put(EndpointConstant.VENDORS_BASE_PATH + "/" + idToUpdate)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedVendor)));
  }

  @Test
  void testUpdateNonExistingVendor() throws Exception {
    // Arrange
    var idToUpdate = 1;
    when(vendorRepository.findById(idToUpdate)).thenReturn(Optional.empty());
    var expectedApiError =
        new ApiError("resource_not_found", "Vendor not found", HttpStatus.NOT_FOUND.value());

    // Act & Assert
    var request = UpdateVendorRequest.builder().name(NEW_VENDOR_NAME).build();

    mockMvc
        .perform(
            MockMvcRequestBuilders.put(EndpointConstant.VENDORS_BASE_PATH + "/" + idToUpdate)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    verify(vendorRepository, never()).save(any());
  }
}
