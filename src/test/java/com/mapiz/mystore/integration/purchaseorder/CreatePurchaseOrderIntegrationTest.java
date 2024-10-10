package com.mapiz.mystore.integration.purchaseorder;

import static com.mapiz.mystore.integration.Constants.KIKES_ID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.purchaseorder.application.dto.CreatePurchaseOrderRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineRequest;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderLineResponse;
import com.mapiz.mystore.purchaseorder.application.dto.PurchaseOrderResponse;
import com.mapiz.mystore.purchaseorder.infrastructure.EndpointConstant;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderLineRepository;
import com.mapiz.mystore.purchaseorder.infrastructure.persistence.repository.JpaPurchaseOrderRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaProductVendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class CreatePurchaseOrderIntegrationTest extends BaseIntegrationTest {

  private static final String VENDOR_NAME = "Vendor 1";
  private static final Integer PRODUCT_ID = 1;
  private static final Integer PURCHASE_ORDER_ID = 1;
  private static final double PRODUCT_PRICE = 10.0;
  private static final BigDecimal QUANTITY = BigDecimal.valueOf(20);

  @MockBean private JpaVendorRepository vendorRepository;
  @MockBean private JpaPurchaseOrderRepository purchaseOrderRepository;
  @MockBean private JpaPurchaseOrderLineRepository purchaseOrderLineRepository;
  @MockBean private JpaProductVendorRepository productVendorRepository;

  @Test
  void testCreatePurchaseOrder() throws Exception {
    // Arrange
    var savedVendor = buildVendorEntity();
    var savedProduct = buildProductEntity();
    var savedVendorProduct = buildVendorProductEntity(savedProduct, savedVendor);
    var savedPurchaseOrder = buildPurchaseOrderEntity(savedVendor);
    var savedPurchaseOrderLine = buildPurchaseOrderLineEntity(savedProduct);

    mockRepositories(savedVendor, savedVendorProduct, savedPurchaseOrder, savedPurchaseOrderLine);

    var request = buildCreatePurchaseOrderRequest();

    var expectedResponse =
        buildExpectedPurchaseOrderResponse(savedPurchaseOrder, savedPurchaseOrderLine);

    // Act & Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(EndpointConstant.PURCHASE_ORDER_BASE_PATH)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }

  private void mockRepositories(
      VendorEntity vendor,
      VendorProductEntity vendorProduct,
      PurchaseOrderEntity purchaseOrder,
      PurchaseOrderLineEntity purchaseOrderLine) {
    when(vendorRepository.findById(KIKES_ID)).thenReturn(Optional.of(vendor));
    when(productVendorRepository.findByVendorIdAndProductIdIn(KIKES_ID, List.of(PRODUCT_ID)))
        .thenReturn(List.of(vendorProduct));
    when(purchaseOrderRepository.save(any(PurchaseOrderEntity.class))).thenReturn(purchaseOrder);
    when(purchaseOrderLineRepository.saveAll(anyList())).thenReturn(List.of(purchaseOrderLine));
  }

  private VendorEntity buildVendorEntity() {
    return VendorEntity.builder().id(KIKES_ID).name(VENDOR_NAME).build();
  }

  private ProductEntity buildProductEntity() {
    return ProductEntity.builder().id(PRODUCT_ID).name("Product 1").build();
  }

  private VendorProductEntity buildVendorProductEntity(ProductEntity product, VendorEntity vendor) {
    return VendorProductEntity.builder()
        .id(1)
        .product(product)
        .vendor(vendor)
        .price(PRODUCT_PRICE)
        .build();
  }

  private PurchaseOrderEntity buildPurchaseOrderEntity(VendorEntity vendor) {
    return PurchaseOrderEntity.builder()
        .id(PURCHASE_ORDER_ID)
        .createdAt(Instant.now())
        .vendor(vendor)
        .estimatedDeliveryDate(LocalDate.now().plusDays(5))
        .build();
  }

  private PurchaseOrderLineEntity buildPurchaseOrderLineEntity(ProductEntity product) {
    return PurchaseOrderLineEntity.builder()
        .id(PURCHASE_ORDER_ID)
        .quantity(BigDecimal.valueOf(20))
        .unitPrice(PRODUCT_PRICE)
        .product(product)
        .build();
  }

  private CreatePurchaseOrderRequest buildCreatePurchaseOrderRequest() {
    return CreatePurchaseOrderRequest.builder()
        .supplierId(KIKES_ID)
        .estimatedDeliveryDate(LocalDate.now().plusDays(5))
        .purchaseOrderLines(
            List.of(
                PurchaseOrderLineRequest.builder()
                    .productId(PRODUCT_ID)
                    .quantity(QUANTITY)
                    .build()))
        .build();
  }

  private PurchaseOrderResponse buildExpectedPurchaseOrderResponse(
      PurchaseOrderEntity purchaseOrder, PurchaseOrderLineEntity purchaseOrderLine) {
    return PurchaseOrderResponse.builder()
        .id(purchaseOrder.getId())
        .supplierName(VENDOR_NAME)
        .createdAt(purchaseOrder.getCreatedAt())
        .estimatedDeliveryDate(purchaseOrder.getEstimatedDeliveryDate())
        .purchaseOrderLines(
            List.of(
                PurchaseOrderLineResponse.builder()
                    .id(purchaseOrderLine.getId())
                    .unitPrice(purchaseOrderLine.getUnitPrice())
                    .quantity(purchaseOrderLine.getQuantity())
                    .product(ProductResponse.builder().id(PRODUCT_ID).name("Product 1").build())
                    .build()))
        .build();
  }
}
