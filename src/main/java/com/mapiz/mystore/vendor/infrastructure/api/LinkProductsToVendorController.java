package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.vendor.application.command.LinkProductsToVendorCommand;
import com.mapiz.mystore.vendor.application.dto.LinkProductsToVendorRequest;
import com.mapiz.mystore.vendor.application.dto.VendorProductResponse;
import com.mapiz.mystore.vendor.application.usecase.LinkProductsToVendorUseCase;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorProductMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndpointConstant.BASE_PATH)
public class LinkProductsToVendorController {

  private final VendorProductMapper vendorProductMapper;
  private final LinkProductsToVendorUseCase linkProductsToVendorUseCase;

  @PostMapping("/{id}/link-products")
  public @ResponseBody ResponseEntity<List<VendorProductResponse>> linkProducts(
      @PathVariable Integer id, @RequestBody LinkProductsToVendorRequest request) {
    var command =
        LinkProductsToVendorCommand.builder().products(request.getProducts()).vendorId(id).build();
    var result = linkProductsToVendorUseCase.apply(command);
    var response = result.stream().map(vendorProductMapper::modelToProductVendorResponse).toList();
    return ResponseEntity.ok(response);
  }
}
