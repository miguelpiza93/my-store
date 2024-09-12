package com.mapiz.mystore.product.infrastructure.api;

import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.application.mapper.ProductMapper;
import com.mapiz.mystore.product.application.usecase.GetProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mapiz.mystore.product.infrastructure.EndpointConstant.PRODUCTS_BASE_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = PRODUCTS_BASE_PATH)
public class GetProductsController {

    private final GetProductsUseCase getProductsUseCaseImpl;

    @GetMapping
    public @ResponseBody ResponseEntity<List<ProductResponse>> getProducts() {
        var products = getProductsUseCaseImpl.get();
        var response = products.stream().map(ProductMapper.INSTANCE::modelToResponse).toList();
        return ResponseEntity.ok(response);
    }
}
