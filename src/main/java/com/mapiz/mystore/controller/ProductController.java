package com.mapiz.mystore.controller;

import com.mapiz.mystore.entity.Product;
import com.mapiz.mystore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public @ResponseBody List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @DeleteMapping(value = "/{productId}")
    public @ResponseBody ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
