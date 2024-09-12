package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.vendor.application.dto.UpdateVendorRequest;
import com.mapiz.mystore.vendor.application.dto.VendorResponse;
import com.mapiz.mystore.vendor.application.usecase.command.UpdateVendorCommand;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.VendorMapper;
import com.mapiz.mystore.vendor.application.usecase.UpdateVendorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.VENDORS_BASE_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = VENDORS_BASE_PATH)
public class UpdateVendorController {

    private final UpdateVendorUseCase updateVendorUseCase;

    @PutMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<VendorResponse> updateVendor(@PathVariable Integer id, @RequestBody UpdateVendorRequest request) {
        var command = UpdateVendorCommand.builder().id(id).name(request.getName()).build();
        var result =  updateVendorUseCase.apply(command);
        var response = VendorMapper.INSTANCE.modelToVendorResponse(result);
        return ResponseEntity.ok(response);
    }
}
