package com.mapiz.mystore.integration.vendor;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.vendor.application.dto.CreateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.CreateVendorResponse;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class CreateVendorIntegrationTest extends BaseIntegrationTest {

  private static final String VENDOR_NAME = "Vendor 1";
  private static final String PHONE = "1234567890";

  @SpyBean private JpaVendorRepository vendorRepository;

  @Test
  void testCreateVendor() throws Exception {
    // Arrange
    var request = CreateVendorRequest.builder().name(VENDOR_NAME).phone(PHONE).build();

    // Act & Assert
    var expectedVendor = CreateVendorResponse.builder().name(VENDOR_NAME).phone(PHONE).build();
    executeSuccessScenario(request, expectedVendor);
  }

  @Test
  void testCreateVendorWithoutPhone() throws Exception {
    // Arrange
    var request = CreateVendorRequest.builder().name(VENDOR_NAME).build();

    // Act & Assert
    var expectedVendor = CreateVendorResponse.builder().name(VENDOR_NAME).build();
    executeSuccessScenario(request, expectedVendor);
  }

  @Test
  void testCreateVendorWithNullName() throws Exception {
    // Arrange
    var request = CreateVendorRequest.builder().name(null).build();
    var expectedApiError =
        new ApiError("invalid_request", "name: must not be blank", HttpStatus.BAD_REQUEST.value());

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    verify(vendorRepository, never()).save(any());
  }

  private void executeSuccessScenario(
      CreateVendorRequest request, CreateVendorResponse expectedVendor) throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value(expectedVendor.getName()))
        .andExpect(jsonPath("$.phone").value(expectedVendor.getPhone()));
    verify(vendorRepository, times(1)).save(any());
  }
}
