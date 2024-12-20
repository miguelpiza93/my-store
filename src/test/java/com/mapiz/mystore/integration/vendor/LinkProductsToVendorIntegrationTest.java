package com.mapiz.mystore.integration.vendor;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mapiz.mystore.integration.BaseIntegrationTest;
import com.mapiz.mystore.product.application.dto.ProductDetailResponse;
import com.mapiz.mystore.product.infrastructure.persistence.ProductEntity;
import com.mapiz.mystore.product.infrastructure.persistence.repository.JpaProductRepository;
import com.mapiz.mystore.util.BigDecimalUtils;
import com.mapiz.mystore.vendor.application.dto.LinkProductsToVendorRequest;
import com.mapiz.mystore.vendor.application.dto.VendorProductResponse;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.entity.VendorProductEntity;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaProductVendorRepository;
import com.mapiz.mystore.vendor.infrastructure.persistence.repository.JpaVendorRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class LinkProductsToVendorIntegrationTest extends BaseIntegrationTest {

  private static final String VENDOR_NAME = "Vendor 1";

  @MockBean private JpaVendorRepository vendorRepository;

  @MockBean private JpaProductRepository productRepository;

  @MockBean private JpaProductVendorRepository productVendorRepository;

  @Test
  void testLinkProducts() throws Exception {
    // Arrange
    var vendorId = 1;
    var savedVendor = VendorEntity.builder().id(vendorId).name(VENDOR_NAME).build();
    when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(savedVendor));

    var savedProducts =
        List.of(
            ProductEntity.builder().id(1).name("Product 1").description("Description 1").build(),
            ProductEntity.builder().id(2).name("Product 2").description("Description 2").build());
    when(productRepository.findAllById(Set.of(1, 2))).thenReturn(savedProducts);

    var linkedProducts =
        List.of(
            VendorProductEntity.builder()
                .id(1)
                .vendor(savedVendor)
                .product(savedProducts.get(0))
                .price(BigDecimalUtils.valueOf(1.0))
                .build(),
            VendorProductEntity.builder()
                .id(2)
                .vendor(savedVendor)
                .product(savedProducts.get(1))
                .price(BigDecimalUtils.valueOf(1.0))
                .build());
    when(productVendorRepository.findByVendorId(vendorId)).thenReturn(linkedProducts);
    when(productVendorRepository.saveAll(any()))
        .thenReturn(
            List.of(
                VendorProductEntity.builder()
                    .id(1)
                    .vendor(savedVendor)
                    .product(savedProducts.get(0))
                    .price(BigDecimalUtils.valueOf(10.0))
                    .build(),
                VendorProductEntity.builder()
                    .id(2)
                    .vendor(savedVendor)
                    .product(savedProducts.get(1))
                    .price(BigDecimalUtils.valueOf(20.0))
                    .build()));

    // Act & Assert
    var request =
        LinkProductsToVendorRequest.builder()
            .products(Map.of(1, BigDecimal.TEN, 2, BigDecimal.valueOf(20)))
            .build();

    var expectedProductVendors =
        List.of(
            VendorProductResponse.builder()
                .id(1)
                .product(
                    ProductDetailResponse.builder()
                        .id(1)
                        .name("Product 1")
                        .description("Description 1")
                        .build())
                .price(10.0)
                .build(),
            VendorProductResponse.builder()
                .id(2)
                .product(
                    ProductDetailResponse.builder()
                        .id(2)
                        .name("Product 2")
                        .description("Description 2")
                        .build())
                .price(20.0)
                .build());

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(
                    EndpointConstant.BASE_PATH + "/" + vendorId + "/link-products")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expectedProductVendors)));

    verify(productVendorRepository, times(1)).deleteAllById(List.of(1, 2));
  }
}
