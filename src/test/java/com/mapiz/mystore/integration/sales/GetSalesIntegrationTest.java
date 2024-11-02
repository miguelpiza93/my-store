package com.mapiz.mystore.integration.sales;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.sales.application.dto.response.SaleSummary;
import com.mapiz.mystore.sales.infrastructure.EndpointConstant;
import com.mapiz.mystore.util.BigDecimalUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class GetSalesIntegrationTest extends BaseIntegrationTest {

  @Test
  void testGetSales() throws Exception {
    // Act & Assert
    var result =
        mockMvc
            .perform(MockMvcRequestBuilders.get(EndpointConstant.BASE_PATH))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    var sales =
        objectMapper.readValue(result.getResponse().getContentAsString(), SaleSummary[].class);
    assertEquals(1, sales.length);
    var sale = sales[0];
    assertEquals(1, sale.getId());
    assertEquals(BigDecimalUtils.valueOf(1500), sale.getTotal());
    assertEquals("CLOSED", sale.getStatus());
    assertNotNull(sale.getCreatedAt());
  }
}
