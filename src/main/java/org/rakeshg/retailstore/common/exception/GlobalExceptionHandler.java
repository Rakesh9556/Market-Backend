package org.rakeshg.retailstore.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.rakeshg.retailstore.common.exception.business_exception.ResourceForbiddenException;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Resource Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleResourceValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid Request");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad request",
                        message,
                        request.getRequestURI()
                ));
    }

    // 401 — Authentication failure
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiError> handleUnauthorized (SecurityException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        "Unauthorized",
                        e.getMessage(),
                        request.getRequestURI()
                ));
    }

    // 409 - Resource already exist
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ApiError> handleResourceAlreadyExist(ResourceAlreadyExistException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Conflict",
                        e.getMessage(),
                        request.getRequestURI()
                ));
    }

    // 404 - Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        e.getMessage(),
                        request.getRequestURI()
                ));
    }

    // 500
    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ApiError> handleForbiddenException(ResourceForbiddenException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.FORBIDDEN.value(),
                        "Forbidden",
                        e.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        e.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "Something went wrong. Please try again.",
                        request.getRequestURI()
                ));
    }



}
