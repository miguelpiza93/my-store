package com.mapiz.mystore.shared;

import com.mapiz.mystore.product.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknownException(Exception e) {
        ApiError apiError = new ApiError("internal_server_error", e.getMessage(), INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(Exception e) {
        ApiError apiError = new ApiError("resource_not_found", e.getMessage(), NOT_FOUND.value());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleRequestValidationException(MethodArgumentNotValidException e) {
        var errorMessage =
                String.join(", ", e.getBindingResult().getAllErrors().stream()
                        .map(error -> {
                            var fieldName = ((FieldError) error).getField();
                            var validationError = error.getDefaultMessage();
                            return String.format("%s: %s", fieldName, validationError);
                        }).sorted().toList());

        ApiError apiError = new ApiError("invalid_request", errorMessage, BAD_REQUEST.value());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

}
