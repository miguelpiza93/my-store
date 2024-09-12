package com.mapiz.mystore.product.infrastructure.api;

import com.mapiz.mystore.product.application.usecase.DeleteProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mapiz.mystore.product.infrastructure.EndpointConstant.PRODUCTS_BASE_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = PRODUCTS_BASE_PATH)
public class DeleteProductController {

    private final DeleteProductUseCase deleteProductUseCase;

    @DeleteMapping(value = "/{productId}")
    public @ResponseBody ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        deleteProductUseCase.accept(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
