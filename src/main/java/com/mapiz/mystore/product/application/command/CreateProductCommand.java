package com.mapiz.mystore.product.application.command;

import lombok.Data;

@Data
public class CreateProductCommand {

  private String name;
  private String description;
}
