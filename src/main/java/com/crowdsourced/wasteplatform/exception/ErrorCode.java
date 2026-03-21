package com.crowdsourced.wasteplatform.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    VALIDATION("VALIDATION", HttpStatus.BAD_REQUEST, "Validation failed"),
    BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST, "Bad request"),
    AUTH("AUTH", HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN("FORBIDDEN", HttpStatus.FORBIDDEN, "Forbidden"),
    ACCESS_DENIED("ACCESS_DENIED", HttpStatus.FORBIDDEN, "Access denied"),
    NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND, "Resource not found"),
    CONFLICT("CONFLICT", HttpStatus.CONFLICT, "Conflict"),
    AREA_NOT_FOUND("AREA_NOT_FOUND", HttpStatus.NOT_FOUND, "Area not found"),
    AREA_INACTIVE("AREA_INACTIVE", HttpStatus.BAD_REQUEST, "Area is inactive"),
    AREA_ROOT_NOT_FOUND("AREA_ROOT_NOT_FOUND", HttpStatus.NOT_FOUND, "Area root not found"),
    WASTE_CATEGORY_NOT_FOUND("WASTE_CATEGORY_NOT_FOUND", HttpStatus.NOT_FOUND, "Waste category not found"),
    REPORT_NOT_FOUND("REPORT_NOT_FOUND", HttpStatus.NOT_FOUND, "Report not found"),
    ASSIGNMENT_NOT_FOUND("ASSIGNMENT_NOT_FOUND", HttpStatus.NOT_FOUND, "Assignment not found"),
    REPORT_ACCESS_DENIED("REPORT_ACCESS_DENIED", HttpStatus.FORBIDDEN, "Report access denied"),
    INVALID_REPORT_STATUS("INVALID_REPORT_STATUS", HttpStatus.CONFLICT, "Invalid report status transition"),
    INVALID_REPORT_STATUS_FOR_CANCEL("INVALID_REPORT_STATUS_FOR_CANCEL", HttpStatus.BAD_REQUEST, "Invalid report status for cancel"),
    INVALID_ASSIGNMENT_STATUS_FOR_CANCEL("INVALID_ASSIGNMENT_STATUS_FOR_CANCEL", HttpStatus.BAD_REQUEST, "Invalid assignment status for cancel"),
    CANCEL_REASON_REQUIRED("CANCEL_REASON_REQUIRED", HttpStatus.BAD_REQUEST, "Cancel reason is required"),
    CAPABILITY_NOT_ACCEPTING("CAPABILITY_NOT_ACCEPTING", HttpStatus.CONFLICT, "Waste capability is not accepting"),
    COLLECTOR_NOT_FOUND("COLLECTOR_NOT_FOUND", HttpStatus.NOT_FOUND, "Collector not found"),
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User not found"),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND, "Role not found"),
    USER_SUSPENDED("USER_SUSPENDED", HttpStatus.CONFLICT, "User is suspended"),
    WORKING_AREA_REQUIRED("WORKING_AREA_REQUIRED", HttpStatus.BAD_REQUEST, "Working area is required"),
    COLLECTOR_ROLE_REQUIRED("COLLECTOR_ROLE_REQUIRED", HttpStatus.BAD_REQUEST, "Collector role is required"),
    SELF_ADMIN_ROLE_PROTECTED("SELF_ADMIN_ROLE_PROTECTED", HttpStatus.CONFLICT, "Cannot remove admin role from yourself"),
    CANNOT_CLEAR_AREA_WHILE_COLLECTOR("CANNOT_CLEAR_AREA_WHILE_COLLECTOR", HttpStatus.CONFLICT, "Cannot clear working area while user still has collector role"),
    COLLECTOR_AREA_REQUIRED("COLLECTOR_AREA_REQUIRED", HttpStatus.BAD_REQUEST, "Collector area is required"),
    COLLECTOR_AREA_MISMATCH("COLLECTOR_AREA_MISMATCH", HttpStatus.BAD_REQUEST, "Collector area mismatch"),
    ASSIGNMENT_ALREADY_EXISTS("ASSIGNMENT_ALREADY_EXISTS", HttpStatus.CONFLICT, "Assignment already exists"),
    VOUCHER_NOT_FOUND("VOUCHER_NOT_FOUND", HttpStatus.NOT_FOUND, "Voucher not found"),
    VOUCHER_CODE_ALREADY_EXISTS("VOUCHER_CODE_ALREADY_EXISTS", HttpStatus.CONFLICT, "Voucher code already exists"),
    VOUCHER_INVALID_TIME_RANGE("VOUCHER_INVALID_TIME_RANGE", HttpStatus.BAD_REQUEST, "Voucher time range is invalid"),
    VOUCHER_NOT_ACTIVE("VOUCHER_NOT_ACTIVE", HttpStatus.CONFLICT, "Voucher is inactive"),
    VOUCHER_COMING_SOON("VOUCHER_COMING_SOON", HttpStatus.CONFLICT, "Voucher is coming soon"),
    VOUCHER_EXPIRED("VOUCHER_EXPIRED", HttpStatus.CONFLICT, "Voucher has expired"),
    VOUCHER_OUT_OF_STOCK("VOUCHER_OUT_OF_STOCK", HttpStatus.CONFLICT, "Voucher is out of stock"),
    VOUCHER_INSUFFICIENT_POINTS("VOUCHER_INSUFFICIENT_POINTS", HttpStatus.BAD_REQUEST, "Insufficient points"),
    VOUCHER_REDEEM_FAILED("VOUCHER_REDEEM_FAILED", HttpStatus.CONFLICT, "Voucher redeem failed"),
    INVALID_VOUCHER_STOCK("INVALID_VOUCHER_STOCK", HttpStatus.BAD_REQUEST, "Invalid voucher stock"),
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
