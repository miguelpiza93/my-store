package com.mapiz.mystore.integration.vendor;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.vendor.application.dto.UpdateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateVendorIntegrationTest extends BaseIntegrationTest {

    private static final String VENDOR_NAME = "Vendor 1";
    private static final String NEW_VENDOR_NAME = "Updated Vendor 1";


    @MockBean
    private JpaVendorRepository vendorRepository;

    @Test
    void testUpdateVendor() throws Exception {
        // Arrange
        var idToUpdate = 1;
        var savedVendor = VendorEntity.builder().id(idToUpdate).name(VENDOR_NAME).build();
        var updatedVendor = VendorEntity.builder().id(idToUpdate).name(NEW_VENDOR_NAME).build();
        when(vendorRepository.findById(idToUpdate)).thenReturn(Optional.of(savedVendor));
        when(vendorRepository.save(any())).thenReturn(updatedVendor);


        // Act & Assert
        var request = UpdateVendorRequest.builder().name(NEW_VENDOR_NAME).build();
        var expectedVendor = VendorResponse.builder().id(idToUpdate).name(NEW_VENDOR_NAME).build();
        mockMvc.perform(MockMvcRequestBuilders.put(EndpointConstant.VENDORS_BASE_PATH + "/" + idToUpdate)
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
        var expectedApiError = new ApiError("resource_not_found", "Vendor not found", HttpStatus.NOT_FOUND.value());

        // Act & Assert
        var request = UpdateVendorRequest.builder().name(NEW_VENDOR_NAME).build();

        mockMvc.perform(MockMvcRequestBuilders.put(EndpointConstant.VENDORS_BASE_PATH + "/" + idToUpdate)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

        verify(vendorRepository, never()).save(any());
    }
}
