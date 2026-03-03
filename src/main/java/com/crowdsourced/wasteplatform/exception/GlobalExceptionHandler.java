package com.crowdsourced.wasteplatform.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        ApiResponse<Map<String, String>> body = new ApiResponse<>(
            false,
            ErrorCode.VALIDATION.getCode(),
            ErrorCode.VALIDATION.getDefaultMessage(),
            fieldErrors,
            Instant.now()
        );
        return ResponseEntity.status(ErrorCode.VALIDATION.getStatus()).body(body);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String message = ex.getCustomMessage() != null ? ex.getCustomMessage() : errorCode.getDefaultMessage();
        log.warn("Application error [{}]: {}", errorCode.getCode(), message);
        ApiResponse<Void> body = ApiResponse.error(errorCode.getCode(), message);
        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        ApiResponse<Void> body = ApiResponse.error(ErrorCode.FORBIDDEN.getCode(), ErrorCode.FORBIDDEN.getDefaultMessage());
        return ResponseEntity.status(ErrorCode.FORBIDDEN.getStatus()).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthentication(AuthenticationException ex) {
        ApiResponse<Void> body = ApiResponse.error(ErrorCode.AUTH.getCode(), ErrorCode.AUTH.getDefaultMessage());
        return ResponseEntity.status(ErrorCode.AUTH.getStatus()).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflict(DataIntegrityViolationException ex) {
        log.error("Data integrity violation", ex);
        ApiResponse<Void> body = ApiResponse.error(ErrorCode.CONFLICT.getCode(), ErrorCode.CONFLICT.getDefaultMessage());
        return ResponseEntity.status(ErrorCode.CONFLICT.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
        log.error("Unhandled exception", ex);
        ApiResponse<Void> body = ApiResponse.error(ErrorCode.INTERNAL.getCode(), ErrorCode.INTERNAL.getDefaultMessage());
        return ResponseEntity.status(ErrorCode.INTERNAL.getStatus()).body(body);
    }
}
