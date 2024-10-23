package com.mapiz.mystore.vendor.infrastructure.api;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.vendor.application.dto.UpdateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.application.mapper.VendorMapper;
import com.mapiz.mystore.vendor.application.usecase.UpdateVendorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class UpdateVendorController {

  private final UpdateVendorUseCase updateVendorUseCase;

  @PutMapping("/{id}")
  public @ResponseBody ResponseEntity<VendorResponse> updateVendor(
      @PathVariable Integer id, @RequestBody UpdateVendorRequest request) {
    var command = VendorMapper.INSTANCE.updateRequestToCommand(request).toBuilder().id(id).build();
    var result = updateVendorUseCase.apply(command);
    var response = VendorMapper.INSTANCE.modelToVendorResponse(result);
    return ResponseEntity.ok(response);
  }
}
