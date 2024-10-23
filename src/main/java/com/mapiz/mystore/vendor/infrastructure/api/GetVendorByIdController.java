package com.mapiz.mystore.vendor.infrastructure.api;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.application.mapper.VendorMapper;
import com.mapiz.mystore.vendor.application.usecase.GetVendorByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class GetVendorByIdController {

  private final GetVendorByIdUseCase getVendorByIdUseCase;

  @GetMapping("/{id}")
  public @ResponseBody ResponseEntity<VendorResponse> getVendorById(@PathVariable Integer id) {
    var result = getVendorByIdUseCase.apply(id);
    var response = VendorMapper.INSTANCE.modelToVendorResponse(result);
    return ResponseEntity.ok(response);
  }
}
