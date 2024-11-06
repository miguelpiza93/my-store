package com.mapiz.mystore.integration.unit;

import static com.mapiz.mystore.integration.Constants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.unit.application.dto.CreateUnitRequest;
import com.mapiz.mystore.unit.application.dto.UnitResponse;
import com.mapiz.mystore.unit.infrastructure.EndpointConstant;
import com.mapiz.mystore.unit.infrastructure.persistence.repository.JpaUnitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class CreateUnitIntegrationTest extends BaseIntegrationTest {

  private static final String UNIT_NAME = "Unit 1";
  private static final String UNIT_SYMBOL = "my symbol";
  private static final boolean UNIT_IS_FRACTIONAL = true;
  private static final boolean UNIT_IS_BASE = true;

  @SpyBean private JpaUnitRepository repository;

  @Test
  void testCreateUnit() throws Exception {
    var request =
        CreateUnitRequest.builder()
            .name(UNIT_NAME)
            .symbol(UNIT_SYMBOL)
            .isFractional(UNIT_IS_FRACTIONAL)
            .isBaseUnit(UNIT_IS_BASE)
            .build();

    // Act & Assert
    var expectedUnit =
        UnitResponse.builder()
            .id(NEXT_AVAILABLE_UNIT_ID)
            .name(UNIT_NAME)
            .symbol(UNIT_SYMBOL)
            .isFractional(UNIT_IS_FRACTIONAL)
            .isBaseUnit(UNIT_IS_BASE)
            .build();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedUnit)));

    verify(repository, times(1)).save(any());
  }

  @Test
  void testCreateUnitWithNullName() throws Exception {
    // Arrange
    var request =
        CreateUnitRequest.builder()
            .name(null)
            .symbol(UNIT_SYMBOL)
            .isFractional(UNIT_IS_FRACTIONAL)
            .isBaseUnit(UNIT_IS_BASE)
            .build();
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

    verify(repository, never()).save(any());
  }

  @Test
  void testCreateUnitWithoutSymbol() throws Exception {
    // Arrange
    var request =
        CreateUnitRequest.builder()
            .name(UNIT_NAME)
            .symbol(null)
            .isFractional(UNIT_IS_FRACTIONAL)
            .isBaseUnit(UNIT_IS_BASE)
            .build();
    var expectedApiError =
        new ApiError(
            "invalid_request", "symbol: must not be blank", HttpStatus.BAD_REQUEST.value());

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    verify(repository, never()).save(any());
  }
}
