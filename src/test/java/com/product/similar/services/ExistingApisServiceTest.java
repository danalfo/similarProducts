package com.product.similar.services;

import com.product.similar.application.services.ExistingApisService;
import com.product.similar.domain.ProductDetail;
import com.product.similar.domain.exception.CustomApiException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExistingApisServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGetSimilarProductIds_Success() {
        // Arrange
        ExistingApisService existingApisService = new ExistingApisService(restTemplate);
        List<String> expectedProductIds = Arrays.asList("1", "2", "3");
        ResponseEntity<List<String>> response = new ResponseEntity<>(expectedProductIds, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class),
                eq("12345")
        )).thenReturn(response);

        // Act
        List<String> actualProductIds = existingApisService.getSimilarProductIds("12345");

        // Assert
        assertEquals(expectedProductIds, actualProductIds);
    }

    @Test
    public void testGetSimilarProductIds_NotFound() {
        // Arrange
        ExistingApisService existingApisService = new ExistingApisService(restTemplate);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class),
                eq("12345")
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThrows(CustomApiException.class, () -> {
            existingApisService.getSimilarProductIds("12345");
        });
    }

    @Test
    public void testGetProductDetail_Success() {
        // Arrange
        ExistingApisService existingApisService = new ExistingApisService(restTemplate);
        ProductDetail expectedProductDetail = new ProductDetail("12345", "Example Product", 10.99, true);
        ResponseEntity<ProductDetail> response = new ResponseEntity<>(expectedProductDetail, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(ProductDetail.class),
                eq("12345")
        )).thenReturn(response);

        // Act
        ProductDetail actualProductDetail = existingApisService.getProductDetail("12345");

        // Assert
        assertNotNull(actualProductDetail);
        assertEquals(expectedProductDetail.getId(), actualProductDetail.getId());
        assertEquals(expectedProductDetail.getName(), actualProductDetail.getName());
        assertEquals(expectedProductDetail.getPrice(), actualProductDetail.getPrice());
        assertEquals(expectedProductDetail.isAvailability(), actualProductDetail.isAvailability());
    }

    @Test
    public void testGetProductDetail_NotFound() {
        // Arrange
        ExistingApisService existingApisService = new ExistingApisService(restTemplate);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(ProductDetail.class),
                eq("12345")
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThrows(CustomApiException.class, () -> {
            existingApisService.getProductDetail("12345");
        });
    }


}
