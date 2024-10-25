package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.vendor.application.dto.CreateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.CreateVendorResponse;
import com.mapiz.mystore.vendor.application.usecase.CreateVendorUseCase;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndpointConstant.BASE_PATH)
public class CreateVendorController {

  private final VendorMapper vendorMapper;
  private final CreateVendorUseCase createVendorUseCase;

  @PostMapping
  public @ResponseBody ResponseEntity<CreateVendorResponse> createVendor(
      @Valid @RequestBody CreateVendorRequest request) {
    var result = createVendorUseCase.apply(vendorMapper.requestToCommand(request));
    var response = vendorMapper.modelToCreateVendorResponse(result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
