package com.mapiz.mystore.product.infrastructure.api;

import static com.mapiz.mystore.product.infrastructure.EndpointConstant.BASE_PATH;

import com.mapiz.mystore.product.application.dto.ProductDetailResponse;
import com.mapiz.mystore.product.application.usecase.GetProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH)
public class GetProductController {

  private final GetProductUseCase getProductUseCaseImpl;

  @GetMapping("/{id}")
  public @ResponseBody ResponseEntity<ProductDetailResponse> getProduct(@PathVariable Integer id) {
    var product = getProductUseCaseImpl.apply(id);
    return ResponseEntity.ok(product);
  }
}
