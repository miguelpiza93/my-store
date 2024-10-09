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

    verifyEntitiesAndAssertions(
        result,
        SAUSAGE_ID,
        UNITS_OF_SAUSAGES_IN_STOCK,
        UNIT_ID,
        "12500.00",
        "3000.00",
        "15000.000");

    verifyEmptyStockForProduct(SAUSAGE_ID);
  }

  @Test
  void testRegisterSaleFewItemsUsingTheBaseUnit() throws Exception {
    var quantity = 3;
    var request = createSaleRequest(EGG_ID, quantity, UNIT_ID);
    var result = executePostRequest(request);

    verifyEntitiesAndAssertions(result, EGG_ID, quantity, UNIT_ID, "900.00", "500.00", "1500.000");

    verifyRemainingStockForProduct(EGG_ID, UNITS_OF_EGGS_IN_STOCK - quantity);
  }

  @Test
  void testRegisterSaleFewItemsUsingTheReferenceUnit() throws Exception {
    var quantityOfCartons = 2;
    var request = createSaleRequest(EGG_ID, quantityOfCartons, CARTON_ID);
    var result = executePostRequest(request);

    verifyEntitiesAndAssertions(
        result, EGG_ID, quantityOfCartons, CARTON_ID, "21000.000", "500.00", "30000.0000");

    verifyRemainingStockForProduct(
        EGG_ID, UNITS_OF_EGGS_IN_STOCK - quantityOfCartons * UNITS_PER_CARTON);
  }

  @Test
  void testRegisterSaleWithoutEnoughStock() throws Exception {
    var request = createSaleRequest(SAUSAGE_ID, UNITS_OF_SAUSAGES_IN_STOCK + 1, UNIT_ID);
    var expectedApiError =
        new ApiError(
            "bad_request",
            "Product id: "
                + SAUSAGE_ID
                + ", Wanted: "
                + ((double) (UNITS_OF_SAUSAGES_IN_STOCK + 1))
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

  private RegisterSaleRequest createSaleRequest(int productId, int quantity, int unitId) {
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

  private void verifyEntitiesAndAssertions(
      MvcResult result,
      int productId,
      int quantity,
      int unitId,
      String expectedCost,
      String expectedPrice,
      String expectedTotal)
      throws JsonProcessingException, UnsupportedEncodingException {

    var ids = getSavedIds(result);

    var sale = saleRepository.findById(ids.get(0)).orElseThrow();

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(sale.getProduct().getId()).isEqualTo(productId);
    softly.assertThat(sale.getQuantity()).isEqualTo(quantity);
    softly.assertThat(sale.getUnit().getId()).isEqualTo(unitId);
    softly.assertThat(sale.getCost()).isEqualTo(new BigDecimal(expectedCost));
    softly.assertThat(sale.getPrice()).isEqualTo(new BigDecimal(expectedPrice));
    softly.assertThat(sale.getTotal()).isEqualTo(new BigDecimal(expectedTotal));
    softly.assertThat(sale.getCreatedAt()).isNotNull();
    softly.assertAll();
  }

  private void verifyEmptyStockForProduct(int productId) {
    var availableStock =
        stockItemRepository.findAllAvailable().stream()
            .filter(s -> s.getPurchaseOrderLine().getProduct().getId() == productId)
            .toList();
    assertTrue(availableStock.isEmpty());
  }

  private void verifyRemainingStockForProduct(int productId, double expectedRemainingQuantity) {
    var availableStock =
        stockItemRepository.findAllAvailable().stream()
            .filter(s -> s.getPurchaseOrderLine().getProduct().getId() == productId)
            .toList();
    var sumQuantity = availableStock.stream().mapToDouble(StockItemEntity::getQuantity).sum();
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
