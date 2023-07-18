package com.dia.delievery.product.dto;

import com.dia.delievery.product.entity.Products;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private String productName;
    private int price;
    private String description;

    public ProductResponseDto(Products product) {
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }
}