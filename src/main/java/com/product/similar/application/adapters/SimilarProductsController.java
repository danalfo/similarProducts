package com.product.similar.application.adapters;

import com.product.similar.application.services.SimilarProductService;
import com.product.similar.domain.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class SimilarProductsController {

    private final SimilarProductService similarProductService;

    @GetMapping("/{productId}/similar")
    public List<ProductDetail> getSimilarProducts(@PathVariable String productId) {
        return similarProductService.getSimilarProducts(productId);
    }

}
