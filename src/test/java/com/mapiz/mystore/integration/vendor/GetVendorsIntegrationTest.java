package com.mapiz.mystore.integration.vendor;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetVendorsIntegrationTest extends BaseIntegrationTest {

  @MockBean private JpaVendorRepository vendorRepository;

  @Test
  void testGetVendors() throws Exception {
    // Arrange
    List<VendorEntity> existingVendors =
        Arrays.asList(
            VendorEntity.builder().id(1).name("Vendor 1").build(),
            VendorEntity.builder().id(2).name("Vendor 2").build());
    when(vendorRepository.findAll()).thenReturn(existingVendors);

    // Act & Assert
    var expectedVendors =
        Arrays.asList(
            VendorResponse.builder().id(1).name("Vendor 1").build(),
            VendorResponse.builder().id(2).name("Vendor 2").build());
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.VENDORS_BASE_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedVendors)));
  }

  @Test
  void testGetVendorsReturnsEmptyList() throws Exception {
    // Arrange
    when(vendorRepository.findAll()).thenReturn(List.of());

    // Act & Assert
    mockMvc
        .perform(MockMvcRequestBuilders.get(EndpointConstant.VENDORS_BASE_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[]"));
  }
}
