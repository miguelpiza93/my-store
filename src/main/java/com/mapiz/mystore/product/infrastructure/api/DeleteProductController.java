package com.mapiz.mystore.product.infrastructure.api;

import static com.mapiz.mystore.product.infrastructure.EndpointConstant.PRODUCTS_BASE_PATH;

import com.mapiz.mystore.product.application.usecase.DeleteProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(PRODUCTS_BASE_PATH)
public class DeleteProductController {

  private final DeleteProductUseCase deleteProductUseCase;

  @DeleteMapping("/{productId}")
  public @ResponseBody ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
    deleteProductUseCase.accept(productId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
