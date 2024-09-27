package com.mapiz.mystore.product.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateProductRequest {

  @NotBlank private String name;

  @NotBlank private String description;
}
