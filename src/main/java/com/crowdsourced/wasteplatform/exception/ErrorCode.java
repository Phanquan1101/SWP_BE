package com.crowdsourced.wasteplatform.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    VALIDATION("VALIDATION", HttpStatus.BAD_REQUEST, "Validation failed"),
    BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST, "Bad request"),
    AUTH("AUTH", HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN("FORBIDDEN", HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND, "Resource not found"),
    CONFLICT("CONFLICT", HttpStatus.CONFLICT, "Conflict"),
    AREA_NOT_FOUND("AREA_NOT_FOUND", HttpStatus.NOT_FOUND, "Area not found"),
    WASTE_CATEGORY_NOT_FOUND("WASTE_CATEGORY_NOT_FOUND", HttpStatus.NOT_FOUND, "Waste category not found"),
    REPORT_NOT_FOUND("REPORT_NOT_FOUND", HttpStatus.NOT_FOUND, "Report not found"),
    REPORT_ACCESS_DENIED("REPORT_ACCESS_DENIED", HttpStatus.FORBIDDEN, "Report access denied"),
    INVALID_REPORT_STATUS("INVALID_REPORT_STATUS", HttpStatus.CONFLICT, "Invalid report status transition"),
    INTERNAL("INTERNAL", HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final String code;
    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(String code, HttpStatus status, String defaultMessage) {
        this.code = code;
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
