package com.mapiz.mystore.vendor.infrastructure.api;

import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.application.mapper.ProductMapper;
import com.mapiz.mystore.vendor.application.dto.ProductVendorResponse;
import com.mapiz.mystore.vendor.application.usecase.GetProductsVendorUseCase;
import com.mapiz.mystore.vendor.infrastructure.persistence.mapper.ProductVendorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mapiz.mystore.vendor.infrastructure.EndpointConstant.VENDORS_BASE_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = VENDORS_BASE_PATH)
public class GetProductsVendorController {

    private final GetProductsVendorUseCase getProductsVendorUseCase;

    @GetMapping(value = "/{id}/products")
    public @ResponseBody ResponseEntity<List<ProductVendorResponse>> getProductsVendor(@PathVariable Integer id) {
        var result = getProductsVendorUseCase.apply(id);
        var response = result.stream().map(ProductVendorMapper.INSTANCE::modelToProductVendorResponse).toList();
        return ResponseEntity.ok(response);
    }
}
