package com.product.similar.application.services;

import com.product.similar.domain.ProductDetail;
import com.product.similar.domain.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExistingApisService {

    private final RestTemplate restTemplate;

    @Value("${existingApis.baseURL}")
    private String existingApisBaseUrl;



    public List<String> getSimilarProductIds(String productId) throws CustomApiException {
        String url = existingApisBaseUrl + "/product/{productId}/similarids";
        try {
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<String>>() {},
                    productId
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Product not found with ID: {}, {}", productId, e.getMessage());
            throw new CustomApiException("Product not found", e);
        } catch (RestClientException e) {
            log.error("Error calling API to get similar product IDs", e);
            throw new CustomApiException("Error calling API to get similar product IDs", e);
        }
    }
    public ProductDetail getProductDetail(String productId) {
        String url = existingApisBaseUrl + "/product/{productId}";
        try{
            ResponseEntity<ProductDetail> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    ProductDetail.class,
                    productId
            );
            log.info("Product {}, retrive ", response.getBody().getName());
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e) {
            log.warn("Product Detail not found with ID: {}, {}", productId, e.getMessage());
            throw new CustomApiException("Product not found", e);
        } catch (RestClientException e) {
            log.error("Error calling API to get similar product IDs", e);
            throw new CustomApiException("Error calling API to get similar product IDs", e);
        }
    }
}