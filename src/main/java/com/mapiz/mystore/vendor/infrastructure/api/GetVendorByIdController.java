package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.application.usecase.GetVendorByIdUseCase;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.VENDORS_BASE_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = VENDORS_BASE_PATH)
public class GetVendorByIdController {

    private final GetVendorByIdUseCase getVendorByIdUseCase;

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<VendorResponse> getVendorById(@PathVariable Integer id) {
        var result = getVendorByIdUseCase.apply(id);
        var response = VendorMapper.INSTANCE.modelToVendorResponse(result);
        return ResponseEntity.ok(response);
    }
}
