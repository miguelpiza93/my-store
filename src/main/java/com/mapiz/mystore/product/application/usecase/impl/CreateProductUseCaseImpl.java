package com.mapiz.mystore.product.application.usecase.impl;

import com.mapiz.mystore.product.application.mapper.ProductMapper;
import com.mapiz.mystore.product.application.usecase.CreateProductUseCase;
import com.mapiz.mystore.product.application.command.CreateProductCommand;
import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product apply(CreateProductCommand command) {
        var model = ProductMapper.INSTANCE.commandToModel(command);
        return productRepository.save(model);
    }
}
