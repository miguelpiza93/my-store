package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.vendor.application.usecase.DeleteVendorUseCase;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndpointConstant.BASE_PATH)
public class DeleteVendorController {

  private final DeleteVendorUseCase deleteVendorUseCase;

  @DeleteMapping("/{vendorId}")
  public @ResponseBody ResponseEntity<Void> deleteVendor(@PathVariable Integer vendorId) {
    deleteVendorUseCase.accept(vendorId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
