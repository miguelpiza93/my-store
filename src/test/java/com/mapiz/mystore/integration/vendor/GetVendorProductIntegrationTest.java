package com.mapiz.mystore.integration.vendor;

import static com.mapiz.mystore.integration.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.unit.application.mapper.UnitMapper;
import com.mapiz.mystore.unit.infrastructure.persistence.repository.impl.UnitRepositoryImpl;
import com.mapiz.mystore.util.BigDecimalUtils;
import com.mapiz.mystore.vendor.application.dto.VendorProductDetailResponse;
import com.mapiz.mystore.vendor.application.dto.VendorProductPriceDetail;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetVendorProductIntegrationTest extends BaseIntegrationTest {

  @Autowired private UnitRepositoryImpl unitRepository;

  @Test
  void testGetVendorProductWithPrices() throws Exception {
    // Arrange
    var expectedUnits =
        unitRepository.findAllById(Set.of(CARTON_ID, HALF_CARTON_ID, UNIT_ID)).stream()
            .map(UnitMapper.INSTANCE::modelToResponse)
            .toList();

    var expectedResponse =
        VendorProductDetailResponse.builder()
            .id(EGG_ID)
            .name(EGG_NAME)
            .vendorName(KIKES_NAME)
            .description(EGG_DESCRIPTION)
            .referenceUnit(CARTON_NAME)
            .salePrices(
                List.of(
                    new VendorProductPriceDetail(UNIT_ID, UNIT_NAME, BigDecimalUtils.valueOf(500)),
                    new VendorProductPriceDetail(
                        CARTON_ID, CARTON_NAME, BigDecimalUtils.valueOf(12000)),
                    new VendorProductPriceDetail(
                        HALF_CARTON_ID, HALF_CARTON_NAME, BigDecimalUtils.valueOf(0))))
            .units(expectedUnits)
            .build();
    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                EndpointConstant.BASE_PATH + "/" + KIKES_ID + "/products/" + EGG_ID))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }
}
