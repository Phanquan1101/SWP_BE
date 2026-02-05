package com.crowdsourced.wasteplatform.entity;

public enum UserStatus {
    /**
     * Tài khoản hoạt động bình thường.
     */
    ACTIVE,
    /**
     * Bị đình chỉ (trước đây dùng INACTIVE, đã chuẩn hóa về SUSPENDED).
     */
    SUSPENDED
}
