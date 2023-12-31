package com.dia.delivery.store.dto;

import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.review.dto.ReviewResponseDto;
import com.dia.delivery.review.entity.Reviews;
import com.dia.delivery.store.entity.Stores;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreOneResponseDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;

    private List<ProductResponseDto> productResponseDtoList;
    private List<ReviewResponseDto> reviewsList;
    public StoreOneResponseDto(Stores store) {
        this.name = store.getName();
        this.introduction = store.getIntroduction();
        this.imageUrl = store.getImageUrl();
        this.category = store.getCategory();
        this.productResponseDtoList = store.getProductsList().stream().map(ProductResponseDto::new).toList();
        this.reviewsList = store.getReviewsList().stream().map(ReviewResponseDto::new).toList();
    }
}
