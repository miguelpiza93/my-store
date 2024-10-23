package com.mapiz.mystore.vendor.infrastructure.api;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.vendor.application.dto.VendorProductResponse;
import com.mapiz.mystore.vendor.application.usecase.GetProductsVendorUseCase;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class GetProductsVendorController {

  private final GetProductsVendorUseCase getProductsVendorUseCase;

  @GetMapping("/{id}/products")
  public @ResponseBody ResponseEntity<List<VendorProductResponse>> getProductsVendor(
      @PathVariable Integer id) {
    var result = getProductsVendorUseCase.apply(id);
    var response =
        result.stream().map(VendorProductMapper.INSTANCE::modelToProductVendorResponse).toList();
    return ResponseEntity.ok(response);
  }
}
