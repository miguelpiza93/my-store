package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.vendor.application.dto.LinkProductsToVendorRequest;
import com.mapiz.mystore.vendor.application.dto.ProductVendorResponse;
import com.mapiz.mystore.vendor.application.usecase.command.LinkProductsToVendorCommand;
import com.mapiz.mystore.vendor.application.usecase.LinkProductsToVendorUseCase;
import com.mapiz.mystore.vendor.infrastructure.EndpointConstant;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.ProductVendorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = EndpointConstant.VENDORS_BASE_PATH)
public class LinkProductsToVendorController {

    private final LinkProductsToVendorUseCase linkProductsToVendorUseCase;

    @PostMapping(value = "/{id}/link-products")
    public @ResponseBody ResponseEntity<List<ProductVendorResponse>> linkProducts(
            @PathVariable Integer id, @RequestBody LinkProductsToVendorRequest request
    ) {
        var command = LinkProductsToVendorCommand.builder().products(request.getProducts()).vendorId(id).build();
        var result = linkProductsToVendorUseCase.apply(command);
        var response = result.stream().map(ProductVendorMapper.INSTANCE::modelToProductVendorResponse).toList();
        return ResponseEntity.ok(response);
    }
}
