package com.mapiz.mystore.vendor.infrastructure.api;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.vendor.application.dto.VendorProductDetailResponse;
import com.mapiz.mystore.vendor.application.usecase.GetVendorProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class GetVendorProductController {

  private final GetVendorProductUseCase getProductUseCaseImpl;

  @GetMapping("/{vendorId}/products/{productId}")
  public @ResponseBody ResponseEntity<VendorProductDetailResponse> getProduct(
      @PathVariable Integer vendorId, @PathVariable Integer productId) {
    var product = getProductUseCaseImpl.apply(vendorId, productId);
    return ResponseEntity.ok(product);
  }
}
