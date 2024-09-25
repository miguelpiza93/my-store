package com.mapiz.mystore.product.application.usecase.command;

import lombok.Data;

@Data
public class CreateProductCommand {

    private String name;
    private String description;
}
