package com.mapiz.mystore.product.application.usecase.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateProductCommand {

    private String name;
    private String description;
}
