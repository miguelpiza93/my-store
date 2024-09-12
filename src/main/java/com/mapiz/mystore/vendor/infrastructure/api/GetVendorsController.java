package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.application.usecase.GetVendorsUseCase;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.VENDORS_BASE_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = VENDORS_BASE_PATH)
public class GetVendorsController {

    private final GetVendorsUseCase getVendorsUseCaseImpl;

    @GetMapping
    public @ResponseBody ResponseEntity<List<VendorResponse>> getVendors() {
        var result = getVendorsUseCaseImpl.get();
        var response = result.stream().map(VendorMapper.INSTANCE::modelToVendorResponse).toList();
        return ResponseEntity.ok(response);
    }
}
