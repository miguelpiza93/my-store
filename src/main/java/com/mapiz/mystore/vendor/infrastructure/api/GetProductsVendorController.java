package com.mapiz.mystore.vendor.infrastructure.api;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.VENDORS_BASE_PATH;

import com.mapiz.mystore.vendor.application.dto.VendorVendorResponse;
import com.mapiz.mystore.vendor.application.usecase.GetProductsVendorUseCase;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.ProductVendorMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(VENDORS_BASE_PATH)
public class GetProductsVendorController {

  private final GetProductsVendorUseCase getProductsVendorUseCase;

  @GetMapping("/{id}/products")
  public @ResponseBody ResponseEntity<List<VendorVendorResponse>> getProductsVendor(
      @PathVariable Integer id) {
    var result = getProductsVendorUseCase.apply(id);
    var response =
        result.stream().map(ProductVendorMapper.INSTANCE::modelToProductVendorResponse).toList();
    return ResponseEntity.ok(response);
  }
}
