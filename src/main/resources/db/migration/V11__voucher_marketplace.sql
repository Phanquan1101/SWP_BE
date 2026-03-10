-- V11: Voucher marketplace and redemption flow

CREATE TABLE IF NOT EXISTS vouchers (
    id CHAR(36) NOT NULL,
    code VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    points_cost INT NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    available_from TIMESTAMP NULL,
    available_to TIMESTAMP NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    image_url TEXT NULL,
    created_by CHAR(36) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_vouchers_code (code),
    KEY idx_vouchers_active (is_active),
    KEY idx_vouchers_available_from (available_from),
    KEY idx_vouchers_available_to (available_to),
    CONSTRAINT fk_vouchers_created_by FOREIGN KEY (created_by) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS voucher_redemptions (
    id CHAR(36) NOT NULL,
    voucher_id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL,
    redeemed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    redeem_code VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ISSUED',
    note TEXT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_voucher_redemptions_redeem_code (redeem_code),
    KEY idx_voucher_redemptions_user_time (user_id, redeemed_at),
    KEY idx_voucher_redemptions_voucher (voucher_id),
    CONSTRAINT fk_voucher_redemptions_voucher FOREIGN KEY (voucher_id) REFERENCES vouchers(id),
    CONSTRAINT fk_voucher_redemptions_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;
