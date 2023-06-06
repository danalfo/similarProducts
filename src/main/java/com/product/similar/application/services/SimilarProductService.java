package com.product.similar.application.services;

import com.product.similar.domain.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimilarProductService {

    private final ExistingApisService existingApisService;

    public List<ProductDetail> getSimilarProducts(String productId) {
        List<String> similarProductIds = existingApisService.getSimilarProductIds(productId);

;        return similarProductIds.stream()
                .map(existingApisService::getProductDetail)
                .collect(Collectors.toList());
    }
}