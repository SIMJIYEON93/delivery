package com.dia.delivery.product.dto;

import com.dia.delivery.product.entity.Products;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private String imageUrl;
    private String productName;
    private int price;
    private String description;

    public ProductResponseDto(Products products) {
        this.imageUrl = products.getImageUrl();
        this.productName = products.getProductName();
        this.price = products.getPrice();
        this.description = products.getDescription();
    }
}
