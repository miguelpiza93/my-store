package com.mapiz.mystore.product.application.usecase.impl;

import com.mapiz.mystore.product.domain.Product;
import com.mapiz.mystore.product.domain.repository.ProductRepository;
import com.mapiz.mystore.product.application.usecase.GetProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetProductsUseCaseImpl implements GetProductsUseCase {

    private final ProductRepository productRepository;

    @Override
    public List<Product> get() {
        return productRepository.findAll();
    }
}
