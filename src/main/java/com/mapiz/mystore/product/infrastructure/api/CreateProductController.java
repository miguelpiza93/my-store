package com.mapiz.mystore.product.infrastructure.api;

import static com.mapiz.mystore.product.infrastructure.EndpointConstant.PRODUCTS_BASE_PATH;

import com.mapiz.mystore.product.application.dto.CreateProductRequest;
import com.mapiz.mystore.product.application.dto.ProductResponse;
import com.mapiz.mystore.product.application.mapper.ProductMapper;
import com.mapiz.mystore.product.application.usecase.CreateProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(PRODUCTS_BASE_PATH)
public class CreateProductController {

  private final CreateProductUseCase createProductUseCase;

  @PostMapping
  public @ResponseBody ResponseEntity<ProductResponse> createProduct(
      @Valid @RequestBody CreateProductRequest request) {
    var command = ProductMapper.INSTANCE.requestToCommand(request);
    var product = createProductUseCase.apply(command);
    var response = ProductMapper.INSTANCE.modelToResponse(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
