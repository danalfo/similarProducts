package com.product.similar.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomApiException extends RuntimeException {

    public CustomApiException(String message) {
        super(message);
    }

    public CustomApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
