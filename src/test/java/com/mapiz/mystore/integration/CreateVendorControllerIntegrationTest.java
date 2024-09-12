package com.mapiz.mystore.integration;

import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.application.dto.CreateVendorRequest;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateVendorControllerIntegrationTest extends BaseIntegrationTest {

    private static final String VENDOR_NAME = "Vendor 1";

    @MockBean
    private JpaVendorRepository vendorRepository;

    @Test
    void testCreateVendor() throws Exception {
        // Arrange
        var savedVendor = VendorEntity.builder().id(1).name(VENDOR_NAME).build();
        when(vendorRepository.save(any())).thenReturn(savedVendor);
        var request = CreateVendorRequest.builder().name(VENDOR_NAME).build();

        // Act & Assert
        var expectedVendor = Vendor.builder().id(1).name(VENDOR_NAME).build();
        mockMvc.perform(MockMvcRequestBuilders.post(EndpointConstant.VENDORS_BASE_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedVendor)));
    }

    @Test
    void testCreateVendorWithNullName() throws Exception {
        // Arrange
        var request = CreateVendorRequest.builder().name(null).build();
        var expectedApiError = new ApiError("invalid_request", "name: must not be blank", HttpStatus.BAD_REQUEST.value());


        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post(EndpointConstant.VENDORS_BASE_PATH)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

        verify(vendorRepository, never()).save(any());
    }
}
