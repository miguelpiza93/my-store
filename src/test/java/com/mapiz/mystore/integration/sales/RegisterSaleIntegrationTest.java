package com.mapiz.mystore.integration.sales;

import static com.mapiz.mystore.integration.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.sales.application.dto.request.RegisterSaleRequest;
import com.mapiz.mystore.sales.application.dto.request.RegisterSaleResponse;
import com.mapiz.mystore.sales.application.dto.request.SaleItemRequest;
import com.mapiz.mystore.sales.infrastructure.EndpointConstant;
import com.mapiz.mystore.sales.infrastructure.persistence.repository.JpaSaleRepository;
import com.mapiz.mystore.shared.ApiError;
import com.mapiz.mystore.stock.infrastructure.persistence.entity.StockItemEntity;
import com.mapiz.mystore.stock.infrastructure.persistence.repository.JpaStockItemRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class RegisterSaleIntegrationTest extends BaseIntegrationTest {

  @SpyBean private JpaSaleRepository saleRepository;
  @SpyBean private JpaStockItemRepository stockItemRepository;

  @Test
  void testRegisterSaleBuyingAllStockUsingTheBaseUnit() throws Exception {
    var request = createSaleRequest(SAUSAGE_ID, UNITS_OF_SAUSAGES_IN_STOCK, UNIT_ID);
    var result = executePostRequest(request);

    assertSalePersistedWithExpectedValues(
        result, SAUSAGE_ID, UNITS_OF_SAUSAGES_IN_STOCK, UNIT_ID, "12500.0", "3000.0", "15000.0");

    assertStockIsEmptyForProduct(SAUSAGE_ID);
  }

  @Test
  void testRegisterSaleFewItemsUsingTheBaseUnit() throws Exception {
    var quantity = BigDecimalUtils.valueOf(3);
    var request = createSaleRequest(EGG_ID, quantity, UNIT_ID);
    var result = executePostRequest(request);

    assertSalePersistedWithExpectedValues(
        result, EGG_ID, quantity, UNIT_ID, "900.0", "500.0", "1500.0");

    var expectedRemainingQuantity = BigDecimalUtils.subtract(UNITS_OF_EGGS_IN_STOCK, quantity);
    assertRemainingStockForProduct(EGG_ID, expectedRemainingQuantity);
  }

  @Test
  void testRegisterSaleFewItemsUsingTheReferenceUnit() throws Exception {
    var quantityOfCartons = BigDecimalUtils.valueOf(2.0);
    var request = createSaleRequest(EGG_ID, quantityOfCartons, CARTON_ID);
    var result = executePostRequest(request);

    assertSalePersistedWithExpectedValues(
        result, EGG_ID, quantityOfCartons, CARTON_ID, "21000.0", "500.0", "30000.0");

    var expectedRemainingQuantity =
        BigDecimalUtils.subtract(
            UNITS_OF_EGGS_IN_STOCK, BigDecimalUtils.multiply(quantityOfCartons, UNITS_PER_CARTON));

    assertRemainingStockForProduct(EGG_ID, expectedRemainingQuantity);
  }

  @Test
  void testRegisterSaleWithoutEnoughStock() throws Exception {
    var quantity = BigDecimalUtils.add(UNITS_OF_SAUSAGES_IN_STOCK, BigDecimal.ONE);
    var request = createSaleRequest(SAUSAGE_ID, quantity, UNIT_ID);
    var expectedApiError =
        new ApiError(
            "bad_request",
            "Product id: "
                + SAUSAGE_ID
                + ", Wanted: "
                + quantity
                + ", Have: "
                + UNITS_OF_SAUSAGES_IN_STOCK,
            HttpStatus.BAD_REQUEST.value());

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedApiError)));

    verify(saleRepository, never()).saveAll(any());
    verify(stockItemRepository, never()).saveAll(any());
  }

  // Métodos auxiliares para simplificar y reutilizar el código

  private RegisterSaleRequest createSaleRequest(int productId, BigDecimal quantity, int unitId) {
    return RegisterSaleRequest.builder()
        .items(
            List.of(
                SaleItemRequest.builder()
                    .productId(productId)
                    .quantity(quantity)
                    .unitId(unitId)
                    .build()))
        .build();
  }

  private MvcResult executePostRequest(RegisterSaleRequest request) throws Exception {
    return mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();
  }

  private void assertSalePersistedWithExpectedValues(
      MvcResult result,
      int productId,
      BigDecimal quantity,
      int unitId,
      String expectedCost,
      String expectedPrice,
      String expectedTotal)
      throws JsonProcessingException, UnsupportedEncodingException {

    var ids = getSavedIds(result);
    var sale = saleRepository.findById(ids.get(0)).orElseThrow();

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(sale.getUnit().getId()).isEqualTo(unitId);
    softly.assertThat(sale.getProduct().getId()).isEqualTo(productId);
    softly.assertThat(BigDecimalUtils.compare(sale.getQuantity(), quantity)).isEqualTo(0);
    softly
        .assertThat(BigDecimalUtils.compare(sale.getCost(), BigDecimalUtils.valueOf(expectedCost)))
        .isEqualTo(0);
    softly
        .assertThat(
            BigDecimalUtils.compare(sale.getPrice(), BigDecimalUtils.valueOf(expectedPrice)))
        .isEqualTo(0);
    softly
        .assertThat(
            BigDecimalUtils.compare(sale.getTotal(), BigDecimalUtils.valueOf(expectedTotal)))
        .isEqualTo(0);
    softly.assertThat(sale.getCreatedAt()).isNotNull();
    softly.assertAll();
  }

  private void assertStockIsEmptyForProduct(int productId) {
    var availableStock =
        stockItemRepository.findAllAvailable().stream()
            .filter(s -> s.getPurchaseOrderLine().getProduct().getId() == productId)
            .toList();
    assertTrue(availableStock.isEmpty());
  }

  private void assertRemainingStockForProduct(int productId, BigDecimal expectedRemainingQuantity) {
    var availableStock =
        stockItemRepository.findAllAvailable().stream()
            .filter(s -> s.getPurchaseOrderLine().getProduct().getId() == productId)
            .toList();
    var sumQuantity =
        availableStock.stream()
            .map(StockItemEntity::getQuantity)
            .reduce(BigDecimal.ZERO, BigDecimalUtils::add);
    assertFalse(availableStock.isEmpty());
    assertEquals(expectedRemainingQuantity, sumQuantity);
  }

  private List<Integer> getSavedIds(MvcResult result)
      throws JsonProcessingException, UnsupportedEncodingException {
    return objectMapper
        .readValue(result.getResponse().getContentAsString(), RegisterSaleResponse.class)
        .getSavedIds();
  }
}
